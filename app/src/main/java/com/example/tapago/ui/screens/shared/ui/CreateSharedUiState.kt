package com.example.tapago.ui.screens.shared.ui

import com.example.tapago.models.Exercise
import com.example.tapago.models.Workout

data class CreateSharedUiState(
    val workout: Workout? = null,
    val letter: String? = "",
    val workoutName: String? = "",
    val description: String? = "",
    val exercises: List<Exercise>? = listOf(
        Exercise(
            id = "",
            name = "",
            obs = "",
            set = "",
            rep = "",
            weight = "",
            backboard = ""
        )
    ),
)