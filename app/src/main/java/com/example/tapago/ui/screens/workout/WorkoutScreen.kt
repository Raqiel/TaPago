package com.example.tapago.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tapago.models.Workout
import com.example.tapago.models.mockExercises
import com.example.tapago.ui.components.EmbossedCard
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.Description
import com.example.tapago.ui.theme.FeaturedText
import com.example.tapago.ui.theme.SuperFeaturedTitle
import com.example.tapago.ui.theme.Title


@Composable
//provavelmente é mais seguro passar esse valor la pro viewmodel
fun WorkoutScreen(workout: Workout) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundCardsColor)
    ) {

        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(200.dp)
        ) {
            EmbossedCard(modifier = Modifier.weight(1f)) {
                Text(
                    text = workout.letter,
                    style = SuperFeaturedTitle
                )
            }
            Spacer(Modifier.width(20.dp))
            EmbossedCard(modifier = Modifier.weight(3f)) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = workout.name ?: "",
                        style = Title
                    )
                    Text(
                        text = workout.description ?: "",
                        style = Description
                    )
                }
            }

        }
        ExercisesView(workout)

    }
}

@Composable
fun ExercisesView(workout: Workout) {

    Column(modifier = Modifier.padding(32.dp)) {
        workout.exercises?.forEach {
            EmbossedCard(modifier = Modifier.wrapContentHeight()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = it.name ?: "",
                        style = FeaturedText,
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = it.obs ?: "",
                        style = Title,
                    )
                    Spacer(Modifier.height(20.dp))

                    Row() {
                        Column(modifier = Modifier.weight(1f)) {

                            Text(
                                text = "Série",
                                style = Description
                            )
                            Text(
                                text = it.set ?: "",
                                style = Description
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Repetição",
                                style = Description
                            )
                            Text(
                                text = it.rep ?: "",
                                style = Description
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Peso",
                                style = Description
                            )
                            Text(
                                text = it.weight ?: "",
                                style = Description
                            )
                        }

                    }

                }
            }

        }
    }
}

@Preview
@Composable
fun WorkoutScreenPreview() {
    WorkoutScreen(
        workout = Workout(
            id = 1,
            letter = "A",
            name = "Inferiores I",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            photo = null,
            exercises = mockExercises
        )
    )
}