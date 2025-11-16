package com.example.tapago.ui.screens.home

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.tapago.Destination
import com.example.tapago.R
import com.example.tapago.models.Workout
import com.example.tapago.models.mockWorkouts
import com.example.tapago.ui.components.AppFloatingButton
import com.example.tapago.ui.components.CustomScaffold
import com.example.tapago.ui.components.FeaturedText
import com.example.tapago.ui.components.MediumText
import com.example.tapago.ui.components.WorkoutCardGlass
import com.example.tapago.ui.components.WorkoutCardView
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.Black
import com.example.tapago.ui.theme.Description
import com.example.tapago.ui.theme.FeaturedColor
import com.example.tapago.ui.theme.LightColor
import com.example.tapago.ui.theme.NewYellow
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.newSingleThreadContext
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun HomeScreen(
    navigate: (Destination) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.load()
    }
    HomeContent(
        navigate = navigate,
        workouts = uiState.workouts
    )

}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun HomeContent(
    navigate: (Destination) -> Unit,
    workouts: List<Workout>?
) {
    val hazeState = rememberHazeState()



    CustomScaffold(
        onBackClick = {},
        hasTopBar = true
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
                    Spacer(Modifier.height(20.dp))
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

                        Column(Modifier.padding(horizontal = 20.dp)) {
                            MediumText(
                                text = "Já treinou hoje frango?",
                                color = NewYellow
                            )
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                items(items = workouts ?: emptyList()) {
                                    WorkoutCardGlass(
                                        hazeState = null,
                                        titleText = it.name ?: "",
                                        descriptionText = it.description,
                                        letterText = it.letter,
                                        onCardClick = {
                                            navigate(Destination.Workout(workout = it))
                                        },
                                        onDelete = {
                                        },
                                        onLongClick = {
                                        },
                                        onBackClick = {}
                                    )
                                }
                            }
                            Spacer(Modifier.height(20.dp))
                        }
                    }
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

                        Column(
                            Modifier
                                .padding(20.dp)
                                .height(300.dp)
                        ) {
                            MediumText(
                                text = "Este mês",
                                color = NewYellow
                            )
                        }


                    }

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

                        Column(
                            Modifier
                                .padding(20.dp)
                                .height(300.dp)
                        ) {
                            MediumText(
                                text = "não sei o que vai ter aqui",
                                color = NewYellow
                            )
                        }


                    }

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.gif_frango)
                            .decoderFactory(ImageDecoderDecoder.Factory())
                            .build(),
                        contentDescription = "Logo frango",
                        modifier = Modifier.size(100.dp)  .align(Alignment.BottomCenter)
                    )

                }
            }
                AppFloatingButton(
                    modifier = Modifier
                        .zIndex(10f)
                        .align(Alignment.BottomEnd),
                    onClick = { navigate(Destination.CreateWorkout()) })


            }


    }

}


@Composable
private fun WorkoutsView(
    workouts: List<Workout>?,
    navigate: (Destination) -> Unit,
    onWorkoutClicked: (Workout) -> Unit
) {

    if (workouts != null) {
        WorkoutView(
            longClick = {},
            navigate = navigate,
            workouts = workouts,
            onWorkoutClicked = onWorkoutClicked
        )
    } else {
        EmptyWorkouts(
            navigate = navigate
        )
    }
}


@Composable
private fun WorkoutView(
    longClick: (Boolean) -> Unit,
    workouts: List<Workout>?,
    navigate: (Destination) -> Unit,
    onWorkoutClicked: (Workout) -> Unit
) {
    val scroll = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(scroll)
                .clickable {
                },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gym),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = FillBounds
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.padding(20.dp)) {
                    FeaturedText(text = "Já treinou hoje frango?".uppercase(Locale.ROOT))
                    Spacer(modifier = Modifier.height(20.dp))
//                    workouts?.forEach {
//                        WorkoutCardGlass(
//                            titleText = it.name ?: "",
//                            descriptionText = it.description,
//                            letterText = it.letter,
//                            onCardClick = {
//                                onWorkoutClicked(it)
//                            },
//                            onDelete = {
//                            },
//                            onLongClick = {
//                            },
//                            onBackClick = {}
//                        )
//                    }


                }
            }
        }
        AppFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = { navigate(Destination.CreateWorkout()) })
    }
}


@Composable
fun EmptyWorkouts(navigate: (Destination) -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FeaturedText(
                text = "Você não possui treinos".uppercase(Locale.ROOT),
                color = LightColor
            )
            Spacer(Modifier.height(50.dp))
            Text(
                text = "Criar um treino",
                style = Description,
                modifier = Modifier.clickable {
                    navigate(Destination.CreateWorkout())
                },
                color = FeaturedColor
            )
        }
        AppFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = { navigate(Destination.CreateWorkout()) }
        )
    }
}


//TODO preciso ver o que passar pro parametro workout dao e arrumar o peview
//@RequiresApi(Build.VERSION_CODES.P)
//@SuppressLint("ViewModelConstructorInComposable")
//@Preview
//@Composable
//fun HomeScreenPreview(
//  @PreviewParameter(HomeUiStateProvider::class) state: HomeUiState
//) {
//HomeScreen(navigate = {}, viewModel = HomeViewModel(initialState = state))
//}


@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
fun HomeContentPreview() {
    HomeContent({}, mockWorkouts)
}