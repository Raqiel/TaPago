package com.example.tapago.ui.screens.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.tapago.models.Workout
import com.example.tapago.models.mockExercises

internal class HomeUiStateProvider :
    PreviewParameterProvider<HomeUiState> {
    override val values: Sequence<HomeUiState> = sequenceOf(
        HomeUiState(),
        HomeUiState(
            workouts = listOf(

                Workout(
                    id = 0,
                    letter = "A",
                    name = "Inferiores I",
                    description = null,
                    photo = null,
                    exercises = mockExercises
                ),
                Workout(
                    id = 2,
                    letter = "B",
                    name = "Superiores I",
                    description = null,
                    photo = null,
                    exercises = mockExercises
                ),
                Workout(
                    id = 3,
                    letter = "C",
                    name = "Inferiores II",
                    description = null,
                    photo = null,
                    exercises = mockExercises
                ),
                Workout(
                    id = 4,
                    letter = "D",
                    name = "Superiores II",
                    description = null,
                    photo = null,
                    exercises = mockExercises
                ),
                Workout(
                    id = 5,
                    letter = "D",
                    name = "Full body",
                    description = null,
                    photo = null,
                    exercises = mockExercises
                ),
            )
        ),

        )
}

data class HomeUiState(
    val workouts: List<Workout>? = null
)