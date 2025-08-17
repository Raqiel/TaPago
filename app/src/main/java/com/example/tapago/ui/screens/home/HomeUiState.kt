package com.example.tapago.ui.screens.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.tapago.models.Workout

internal class HomeUiStateProvider :
    PreviewParameterProvider<HomeUiState> {
    override val values: Sequence<HomeUiState> = sequenceOf(
        HomeUiState(),
        HomeUiState(
            workouts = listOf(

                Workout(
                    id = "1",
                    letter = "A",
                    name = "Inferiores I",
                    description = null,
                    photo = null
                ),
                Workout(
                    id = "2",
                    letter = "B",
                    name = "Superiores I",
                    description = null,
                    photo = null
                ),
                Workout(
                    id = "3",
                    letter = "C",
                    name = "Inferiores II",
                    description = null,
                    photo = null
                ),
                Workout(
                    id = "4",
                    letter = "D",
                    name = "Superiores II",
                    description = null,
                    photo = null
                ),
                Workout(
                    id = "5",
                    letter = "D",
                    name = "Full body",
                    description = null,
                    photo = null
                ),
            )
        ),

        )
}

data class HomeUiState(
    val workouts: List<Workout>? = null
)