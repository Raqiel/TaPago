package com.example.tapago.ui.screens.home

import android.annotation.SuppressLint
import android.widget.GridView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tapago.R
import com.example.tapago.ui.components.DebossedCard
import com.example.tapago.ui.components.EmbossedCard
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.Description
import com.example.tapago.ui.theme.FeaturedText
import com.example.tapago.ui.theme.SuperFeaturedText
import com.example.tapago.ui.theme.Title
import com.example.tapago.ui.theme.Yellow300
import com.example.tapago.ui.theme.Yellow50

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    Column(
        Modifier
            .fillMaxSize()
            .background(BackgroundCardsColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))
        WorkoutsView(uiState)
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.gif_frango),
            contentDescription = "Logo frango",
            modifier = Modifier.size(100.dp)
        )
    }
}


@Composable
private fun WorkoutsView(uiState: HomeUiState) {

    val workouts = uiState.workouts
    Spacer(Modifier.height(20.dp))
    Text(
        text = stringResource(R.string.my_workouts),
        style = FeaturedText,
        modifier = Modifier.padding(start = 16.dp)
    )
    Spacer(Modifier.height(20.dp))
    if (workouts != null) {
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(workouts) { workout ->
                EmbossedCard(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(top = 8.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = "A",
                        style = SuperFeaturedText

                    )
                }
            }
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Você não possui treinos",
                style = Title
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Criar um treino",
                style = Description,
                modifier = Modifier.clickable {

                }
            )
        }

    }


}


@Composable
fun EmptyWorkouts() {

}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun HomeScreenPreview(
    @PreviewParameter(HomeUiStateProvider::class) state: HomeUiState

) {
    HomeScreen(viewModel = HomeViewModel(initialState = state))
}