package com.example.tapago.usecase

import com.example.tapago.data.local.repositories.WorkoutRepository
import com.example.tapago.models.Workout

class CreateWorkoutUseCase(
    private val workoutRepository: WorkoutRepository
) {
    suspend fun invoke(workout: Workout) {
        workoutRepository.insertWorkout(workout)
    }
}
