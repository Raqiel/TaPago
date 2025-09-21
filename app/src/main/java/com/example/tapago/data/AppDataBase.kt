package com.example.tapago.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tapago.models.Workout
import com.example.tapago.ui.screens.shared.data.ExerciseListConverter
import com.example.tapago.ui.screens.shared.data.WorkoutDao


@Database(entities = [Workout::class], version = 1)
@TypeConverters(ExerciseListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

}