package com.example.tapago.data.remote.repositories

import com.example.tapago.data.remote.auth.AuthRepository
import com.example.tapago.models.Exercise
import com.example.tapago.models.Workout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private const val USERS_COLLECTION = "users"
private const val WORKOUTS_COLLECTION = "workouts"

class NotLoggedInException : Exception("Usuário não está logado")

interface RemoteWorkoutRepository {
    fun observeWorkouts(): Flow<List<Workout>>
    suspend fun saveWorkout(workout: Workout): Result<String>
    suspend fun deleteWorkout(workoutId: String): Result<Unit>
}

class RemoteWorkoutRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) : RemoteWorkoutRepository {

    private fun workoutsRef() = authRepository.currentUserId?.let { uid ->
        firestore.collection(USERS_COLLECTION).document(uid).collection(WORKOUTS_COLLECTION)
    }

    override fun observeWorkouts(): Flow<List<Workout>> = callbackFlow {
        val ref = workoutsRef()
        if (ref == null) {
            trySend(emptyList())
            close(NotLoggedInException())
            return@callbackFlow
        }

        val registration: ListenerRegistration = ref
            .orderBy("letter", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val workouts = snapshot?.documents.orEmpty().mapNotNull { doc ->
                    doc.toObject(WorkoutDocument::class.java)?.toWorkout(doc.id)
                }
                trySend(workouts)
            }

        awaitClose { registration.remove() }
    }

    override suspend fun saveWorkout(workout: Workout): Result<String> = runCatching {
        val ref = workoutsRef() ?: throw NotLoggedInException()

        val document = if (workout.id == 0L) ref.document() else ref.document(workout.id.toString())

        document.set(WorkoutDocument.from(workout))
        document.id
    }

    override suspend fun deleteWorkout(workoutId: String): Result<Unit> = runCatching {
        val ref = workoutsRef() ?: throw NotLoggedInException()
        ref.document(workoutId).delete()
        Unit
    }
}

data class WorkoutDocument(
    val letter: String = "",
    val name: String? = null,
    val description: String? = null,
    val photo: String? = null,
    val exercises: List<ExerciseDocument> = emptyList(),
    val updatedAt: Long = 0L
) {
    fun toWorkout(documentId: String) = Workout(
        id = documentId.hashCode().toLong(),
        letter = letter,
        name = name,
        description = description,
        photo = photo,
        exercises = exercises.map { it.toExercise() }
    )

    companion object {
        fun from(workout: Workout) = WorkoutDocument(
            letter = workout.letter,
            name = workout.name,
            description = workout.description,
            photo = workout.photo,
            exercises = workout.exercises.orEmpty().map { ExerciseDocument.from(it) },
            updatedAt = System.currentTimeMillis()
        )
    }
}

data class ExerciseDocument(
    val id: String = "",
    val name: String? = null,
    val obs: String? = null,
    val set: String? = null,
    val rep: String? = null,
    val weight: String? = null,
    val backboard: String? = null,
    val photo: String? = null
) {
    fun toExercise() = Exercise(
        id = id,
        name = name,
        obs = obs,
        set = set,
        rep = rep,
        weight = weight,
        backboard = backboard,
        photo = photo
    )

    companion object {
        fun from(exercise: Exercise) = ExerciseDocument(
            id = exercise.id,
            name = exercise.name,
            obs = exercise.obs,
            set = exercise.set,
            rep = exercise.rep,
            weight = exercise.weight,
            backboard = exercise.backboard,
            photo = exercise.photo
        )
    }
}
