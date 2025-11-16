package com.example.tapago.ui.screens.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tapago.R
import com.example.tapago.models.Workout
import com.example.tapago.models.mockExercises
import com.example.tapago.ui.components.CustomScaffold
import com.example.tapago.ui.components.EmbossedCard
import com.example.tapago.ui.components.FeaturedText
import com.example.tapago.ui.components.MediumText
import com.example.tapago.ui.components.SmallText
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.Black
import com.example.tapago.ui.theme.Description
import com.example.tapago.ui.theme.FeaturedText
import com.example.tapago.ui.theme.NewYellow
import com.example.tapago.ui.theme.SuperFeaturedTitle
import com.example.tapago.ui.theme.Title
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import java.util.Locale


@Composable
//provavelmente é mais seguro passar esse valor la pro viewmodel
fun WorkoutScreen(workout: Workout?) {
    val hazeState = rememberHazeState()
    CustomScaffold(
        onBackClick = {},
        hasTopBar = false
    ) {
        Box() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .hazeSource(hazeState)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.gym),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = FillBounds
                )

            }
            LazyColumn(verticalArrangement = Arrangement.Center) {


                item {
                    Box(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.Transparent)
                            .hazeEffect(
                                state = hazeState,
                                style = HazeStyle(
                                    blurRadius = 8.dp,
                                    backgroundColor = White,
                                    tint = HazeTint(
                                        Black.copy(alpha = 0.6f),
                                    )
                                )
                            )
                    ) {


                        Row(Modifier.padding(horizontal = 20.dp).height(150.dp)) {
                            FeaturedText(
                                text = workout?.letter ?: "A",
                                size = 120
                            )
                            Spacer(Modifier.width(20.dp))
                            Column(){
                                MediumText(
                                    text = workout?.name ?: "Nome do Treino",
                                )
                                SmallText(
                                    text = workout?.description ?: "Nome do Treino",
                                )
                            }


                        }
                    }
                    workout?.exercises?.forEach { _ ->
                        Box(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.Transparent)
                                .hazeEffect(
                                    state = hazeState,
                                    style = HazeStyle(
                                        blurRadius = 8.dp,
                                        backgroundColor = White,
                                        tint = HazeTint(
                                            Black.copy(alpha = 0.6f),
                                        )
                                    )
                                )
                        ) {

                            Row(Modifier.padding(horizontal = 20.dp).height(250.dp)) {

                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                        .border(3.dp, NewYellow, CircleShape)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.img_exercise),
                                        contentDescription = "Profile Picture",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.matchParentSize()
                                    )
                                }
                                Spacer(Modifier.width(20.dp))
                                Column() {
                                    MediumText(
                                        text = workout?.name ?: "Nome do Treino",
                                    )
                                    SmallText(
                                        text = workout?.description ?: "Nome do Treino",
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }}
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