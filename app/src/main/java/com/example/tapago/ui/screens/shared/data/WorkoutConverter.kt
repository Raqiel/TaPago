package com.example.tapago.ui.screens.shared.data

import androidx.room.TypeConverter
import com.example.tapago.models.Exercise
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class ExerciseListConverter {

    @TypeConverter
    fun fromExerciseList(exercises: List<Exercise>?): String? {
        if (exercises == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(exercises)
    }

    @TypeConverter
    fun toExerciseList(json: String?): List<Exercise>? {
        if (json == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Exercise>>() {}.type
        return gson.fromJson(json, type)
    }
}