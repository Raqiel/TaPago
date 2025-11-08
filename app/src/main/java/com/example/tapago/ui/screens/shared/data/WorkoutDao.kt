package com.example.tapago.ui.screens.shared.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tapago.models.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkout(workout: Workout)

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: String): Workout?

    @Query("SELECT * FROM workouts ORDER BY letter ASC")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("DELETE FROM workouts WHERE id = :workoutId")
    suspend fun deleteWorkoutById(workoutId: String)
}