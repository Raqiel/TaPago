package com.example.tapago.ui.screens.workout

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.tapago.models.Workout
import com.example.tapago.models.mockExercises

internal class WorkoutUiStateProvider :
    PreviewParameterProvider<WorkoutUiState> {
    override val values: Sequence<WorkoutUiState> = sequenceOf(
        WorkoutUiState(),
        WorkoutUiState(
            workout =
                Workout(
                    id = 1,
                    letter = "A",
                    name = "Inferiores I",
                    description = null,
                    photo = null,
                    exercises = mockExercises
                )
        ),

        )
}

data class WorkoutUiState(
    val workout: Workout? = null
)