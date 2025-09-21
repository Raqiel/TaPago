package com.example.tapago.data.local.repositories

import com.example.tapago.models.Workout
import com.example.tapago.ui.screens.shared.data.WorkoutDao
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getAllWorkouts(): Flow<List<Workout>>
    suspend fun insertWorkout(workout: Workout)
}


class WorkoutRepositoryImpl(private val workoutDao: WorkoutDao) : WorkoutRepository {
    override fun getAllWorkouts(): Flow<List<Workout>> {
        return workoutDao.getAllWorkouts()
    }

    override suspend fun insertWorkout(workout: Workout) {
        workoutDao.insertWorkout(workout)
    }
}