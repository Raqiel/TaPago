package com.example.tapago.usecase

import com.example.tapago.data.local.repositories.WorkoutRepository
import com.example.tapago.data.local.repositories.WorkoutRepositoryImpl
import com.example.tapago.models.Workout
import kotlinx.coroutines.flow.Flow

class GetWorkoutsUseCase(
    private val workoutRepository: WorkoutRepository

) {
    operator fun invoke(): Flow<List<Workout>>? {
        return workoutRepository.getAllWorkouts()
    }
}