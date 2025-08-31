package com.example.tapago.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.tapago.Destination
import com.example.tapago.R
import com.example.tapago.models.mockWorkouts
import com.example.tapago.ui.components.AppFloatingButton
import com.example.tapago.ui.components.CustomScaffold
import com.example.tapago.ui.components.WorkoutCardView
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.Description
import com.example.tapago.ui.theme.FeaturedColor
import com.example.tapago.ui.theme.NormalColor
import com.example.tapago.ui.theme.Oswald
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun HomeScreen(
    navigate: (Destination) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    CustomScaffold(
        onBackClick = {},
        hasTopBar = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(300.dp)
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .background(BackgroundCardsColor.copy(alpha = 0.3f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(30.dp))
                WorkoutsView(uiState, navigate)
                Spacer(Modifier.weight(1f))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.gif_frango)
                        .decoderFactory(ImageDecoderDecoder.Factory())
                        .build(),
                    contentDescription = "Logo frango",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}


@Composable
private fun WorkoutsView(
    uiState: HomeUiState,
    navigate: (Destination) -> Unit
) {

    val workouts = uiState.workouts

    if (workouts != null) {
        WorkoutView(
            longClick = {}
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
) {
    val scroll = rememberScrollState()
    Box(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(BackgroundColor)
                .verticalScroll(scroll)
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    longClick(false)
                },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.height(300.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Já treinou hoje frango?".uppercase(Locale.ROOT),
                        color = FeaturedColor,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 60.sp,
                        fontFamily = Oswald
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    mockWorkouts.forEach {
                        WorkoutCardView(
                            titleText = it.name ?: "",
                            descriptionText = it.description,
                            letterText = it.letter,
                            onCardClick = {
                            },
                            onDelete = {
                            },
                            onLongClick = {
                            }
                        )
                    }
                }
            }
        }
        AppFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = {})
    }
}


@Composable
fun EmptyWorkouts(navigate: (Destination) -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Você não possui treinos".uppercase(Locale.ROOT),
                color = NormalColor,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 60.sp,
                fontFamily = Oswald
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
            onClick = {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun HomeScreenPreview(
    @PreviewParameter(HomeUiStateProvider::class) state: HomeUiState
) {
    HomeScreen(navigate = {}, viewModel = HomeViewModel(initialState = state))
}