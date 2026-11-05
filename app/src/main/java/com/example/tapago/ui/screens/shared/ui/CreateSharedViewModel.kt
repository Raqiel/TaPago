package com.example.tapago.ui.screens.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.net.Uri
import com.example.tapago.Destination
import com.example.tapago.data.remote.repositories.PhotoRepository
import com.example.tapago.models.Exercise
import com.example.tapago.models.Workout
import com.example.tapago.usecase.CreateWorkoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.UUID

class CreateSharedViewModel(
    initialState: CreateSharedUiState = CreateSharedUiState(),
    private val createWorkoutUseCase: CreateWorkoutUseCase,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<CreateSharedUiState> = _uiState.asStateFlow()

    private val workoutPhotoId = UUID.randomUUID().toString()

    fun onWorkoutNameChanged(workoutName: String) {
        _uiState.value = _uiState.value.copy(workoutName = workoutName)
    }

    fun onLetterChanged(workoutLetter: String) {
        _uiState.value = _uiState.value.copy(letter = workoutLetter)
    }

    fun onDescriptionChanged(workoutDescription: String) {
        _uiState.value = _uiState.value.copy(description = workoutDescription)
    }

    fun onPhotoSelected(uri: Uri?) {
        _uiState.value = _uiState.value.copy(photoUri = uri?.toString())
    }

    fun onExerciseNameChanged(exerciseId: String, name: String) {
        updateExercise(exerciseId) { it.copy(name = name) }
    }

    fun onObsChanged(exerciseId: String, obs: String) {
        updateExercise(exerciseId) { it.copy(obs = obs) }
    }

    fun onExercisePhotoSelected(exerciseId: String, uri: Uri?) {
        if (uri == null) return
        updateExercise(exerciseId) { it.copy(photo = uri.toString()) }
    }

    fun onFieldChanged(exerciseId: String, field: ExerciseField, value: String) {
        val normalized = value.replace('.', ',')
        val pattern = if (field == ExerciseField.WEIGHT) DECIMAL_PATTERN else INTEGER_PATTERN
        if (!pattern.matches(normalized)) return
        updateExercise(exerciseId) { it.withValue(field, normalized) }
    }

    fun onIncrease(exerciseId: String, field: ExerciseField) {
        stepField(exerciseId, field, BigDecimal.ONE)
    }

    fun onDecrease(exerciseId: String, field: ExerciseField) {
        stepField(exerciseId, field, BigDecimal.ONE.negate())
    }

    fun addNewExercise() {
        val currentExercises = _uiState.value.exercises.orEmpty()
        _uiState.value = _uiState.value.copy(exercises = currentExercises + Exercise.empty())
    }

    fun removeExercise(exerciseId: String) {
        val currentExercises = _uiState.value.exercises.orEmpty()
        _uiState.value = _uiState.value.copy(
            exercises = currentExercises.filterNot { it.id == exerciseId }
        )
    }

    fun onSaveWorkout(navigate: (Destination) -> Unit) {
        val state = _uiState.value
        if (state.isSaving) return

        val exercises = state.exercises.orEmpty().filterNot { it.isBlank() }
        val validationError = when {
            state.letter.isNullOrBlank() -> "Volte e escolha a letra do treino"
            exercises.isEmpty() -> "Adicione pelo menos um exercício"
            exercises.any { it.name.isNullOrBlank() } -> "Todo exercício precisa de um nome"
            else -> null
        }
        if (validationError != null) {
            _uiState.value = state.copy(errorMessage = validationError)
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)

            val newWorkout = runCatching {
                Workout(
                    id = state.workout?.id ?: 0L,
                    letter = state.letter.orEmpty(),
                    name = state.workoutName.orEmpty(),
                    description = state.description.orEmpty(),
                    photo = state.photoUri?.let { uploadWorkoutPhoto(it) },
                    exercises = exercises.map { exercise ->
                        exercise.copy(photo = exercise.photo?.let { uploadExercisePhoto(exercise.id, it) })
                    }
                )
            }.getOrElse { throwable ->
                showError("Não consegui enviar as fotos: ${throwable.message}")
                return@launch
            }

            runCatching { createWorkoutUseCase.invoke(newWorkout) }
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isSaving = false)
                    navigate(Destination.Home())
                }
                .onFailure { throwable ->
                    showError(throwable.message ?: "Não consegui salvar o treino")
                }
        }
    }

    private suspend fun uploadWorkoutPhoto(uri: String): String =
        photoRepository.uploadWorkoutPhoto(workoutPhotoId, Uri.parse(uri)).getOrThrow()

    private suspend fun uploadExercisePhoto(exerciseId: String, uri: String): String =
        photoRepository.uploadExercisePhoto(exerciseId, Uri.parse(uri)).getOrThrow()

    private fun showError(message: String) {
        _uiState.value = _uiState.value.copy(isSaving = false, errorMessage = message)
    }

    private fun stepField(exerciseId: String, field: ExerciseField, delta: BigDecimal) {
        updateExercise(exerciseId) { exercise ->
            val current = exercise.valueFor(field)?.replace(',', '.')?.toBigDecimalOrNull() ?: BigDecimal.ZERO
            val next = (current + delta).coerceIn(BigDecimal.ZERO, MAX_FIELD_VALUE)
            exercise.withValue(field, next.stripTrailingZeros().toPlainString().replace('.', ','))
        }
    }

    private fun updateExercise(exerciseId: String, updater: (Exercise) -> Exercise) {
        val currentExercises = _uiState.value.exercises.orEmpty()
        _uiState.value = _uiState.value.copy(
            exercises = currentExercises.map { if (it.id == exerciseId) updater(it) else it },
            errorMessage = null
        )
    }

    private companion object {
        val INTEGER_PATTERN = Regex("\\d{0,3}")
        val DECIMAL_PATTERN = Regex("\\d{0,3}(,\\d{0,2})?")
        val MAX_FIELD_VALUE = BigDecimal(999)
    }
}
