package com.example.tapago.ui.screens.shared.ui.create_exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tapago.Destination
import com.example.tapago.R
import com.example.tapago.ui.components.EditNormalText
import com.example.tapago.ui.components.FeaturedText
import com.example.tapago.ui.components.MarombaButton
import com.example.tapago.ui.components.MarombaSecondButton
import com.example.tapago.ui.screens.shared.ui.CreateSharedUiState
import com.example.tapago.ui.screens.shared.ui.CreateSharedViewModel
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.BackgroundLight
import com.example.tapago.ui.theme.FeaturedColor
import com.example.tapago.ui.theme.MediumColor
import com.example.tapago.ui.theme.NormalColor
import com.example.tapago.ui.theme.RobotoRegular

@Composable
fun CreateExerciseScreen(
    navigate: (Destination) -> Unit,
    viewModel: CreateSharedViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    // Este estado local 'exercisesList' não parece estar sendo usado.
    // O ideal é usar apenas o uiState que vem do ViewModel.
    // Se ele não for usado, pode ser removido.

    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FeaturedText(
            text = "Crie um exercício",
            size = 30
        )
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                items = uiState.exercises.orEmpty(),
                key = { index, exercise -> exercise.id.takeIf { it.isNotEmpty() } ?: index }
            ) { index, exercise ->

                AddedExerciseCardView(
                    image = null,
                    onReppChange = { viewModel.onRepChanged(index) },
                    onSettChange = { viewModel.onSetChanged(index) },
                    onWeightChange = { viewModel.onWeightChanged(index) },
                    onBackBoardChange = { viewModel.onBackBoardChanged(index) },
                    onNameChange = { viewModel.onExerciseNameChanged(index) },
                    uiState = uiState,
                    onDecreaseSet = { viewModel.onDecreaseSet(index) },
                    onIncreaseSet = { viewModel.onIncreaseSet(index) },
                    onDecreaseRep = { viewModel.onDecreaseRep(index) },
                    onIncreaseRep = { viewModel.onIncreaseRep(index) },
                    onDecreaseWeight = { viewModel.onDecreaseWeight(index) },
                    onIncreaseWeight = { viewModel.onIncreaseWeight(index) },
                    onDecreaseBackBoard = { viewModel.onDecreaseBackBoard(index) },
                    onIncreaseBackBoard = { viewModel.onIncreaseBackBoard(index) },
                    index = index
                )

                Spacer(modifier = Modifier.height(20.dp))

                EditNormalText(
                    value = exercise.obs ?: "",
                    placeholder = "Descrição do exercício",
                    fontSize = null,
                    onValueChange = { newObs -> viewModel.onObsChanged(index) }
                )

                Spacer(modifier = Modifier.height(30.dp))
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MarombaSecondButton(
                        textButton = null,
                        iconButton = Icons.Filled.Add
                    ) {
                        viewModel.addNewExercise()
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    MarombaButton(
                        textButton = null,
                        iconButton = Icons.Filled.Check
                    ) {
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun AddedExerciseCardView(
    image: Painter?,
    onReppChange: (String) -> Unit,
    onSettChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onBackBoardChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    uiState: CreateSharedUiState,
    onDecreaseSet: () -> Unit,
    onIncreaseSet: () -> Unit,
    onDecreaseRep: () -> Unit,
    onIncreaseRep: () -> Unit,
    onDecreaseWeight: () -> Unit,
    onIncreaseWeight: () -> Unit,
    onDecreaseBackBoard: () -> Unit,
    onIncreaseBackBoard: () -> Unit,
    index: Int,
) {
    Column(
        modifier = Modifier
            .background(BackgroundColor)
    ) {
        SettAndRepView(
            image = image,
            onReppChange = onReppChange,
            onSettChange = onSettChange,
            onWeightChange = onWeightChange,
            onBackBoardChange = onBackBoardChange,
            onNameChange = onNameChange,
            uiState = uiState,
            onDecreaseSett = onDecreaseSet,
            onIncreaseSet = { onIncreaseSet() },
            onDecreaseRep = onDecreaseRep,
            onIncreaseRep = onIncreaseRep,
            onDecreaseWeight = onDecreaseWeight,
            onIncreaseWeight = { onIncreaseWeight() },
            onDecreaseBackBoard = { onDecreaseBackBoard() },
            onIncreaseBackBoard = { onIncreaseBackBoard() },
            index = index
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
}


@Composable
fun SettAndRepView(
    uiState: CreateSharedUiState,
    image: Painter?,
    onReppChange: (String) -> Unit,
    onSettChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onBackBoardChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onDecreaseSett: () -> Unit,
    onIncreaseSet: () -> Unit,
    onDecreaseRep: () -> Unit,
    onIncreaseRep: () -> Unit,
    onDecreaseWeight: () -> Unit,
    onIncreaseWeight: () -> Unit,
    onDecreaseBackBoard: () -> Unit,
    onIncreaseBackBoard: () -> Unit,
    index:Int
) {

    Column(Modifier) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = image ?: painterResource(id = R.drawable.add_photo),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            TextField(
                modifier = Modifier
                    .weight(1f),
                value = uiState.exercises?.get(index)?.name ?: "",
                onValueChange = {
                    onNameChange(it)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = NormalColor,
                    unfocusedTextColor = NormalColor,
                    focusedIndicatorColor = BackgroundColor,
                    unfocusedIndicatorColor = BackgroundColor
                ),
                placeholder = {
                    Text(
                        text = "Nome do treino",
                        color = BackgroundLight,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
            )
            Spacer(modifier = Modifier.width(16.dp))


        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            NumberRefView(
                descriptionColor = MediumColor,
                titleComponent = "Série",
                value = uiState.exercises?.get(index)?.set ?: "",
                onValueChange = onSettChange,
                onDecreaseValue = onDecreaseSett,
                onIncreaseValue = { onIncreaseSet() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            NumberRefView(
                descriptionColor = MediumColor,
                titleComponent = "Repetição",
                value = uiState.exercises?.get(index)?.rep ?: "",
                onValueChange = onReppChange,
                onDecreaseValue = onDecreaseRep,
                onIncreaseValue = onIncreaseRep,
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {

            NumberRefView(
                descriptionColor = MediumColor,
                titleComponent = "Peso",
                value = uiState.exercises?.get(index)?.weight ?: "",
                onValueChange = onWeightChange,
                onDecreaseValue = onDecreaseWeight,
                onIncreaseValue = onIncreaseWeight,
            )

            Spacer(modifier = Modifier.width(16.dp))

            NumberRefView(
                descriptionColor = MediumColor,
                titleComponent = "Encosto",
                value = uiState.exercises?.get(index)?.backboard ?: "",
                onValueChange = onBackBoardChange,
                onDecreaseValue = { onDecreaseBackBoard() },
                onIncreaseValue = onIncreaseBackBoard,
            )
        }
    }
}


@Composable
fun NumberRefView(
    modifier: Modifier = Modifier,
    descriptionColor: Color,
    titleComponent: String,
    value: String,
    onValueChange: (String) -> Unit,
    onDecreaseValue: () -> Unit,
    onIncreaseValue: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = titleComponent,
            color = descriptionColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            fontFamily = RobotoRegular
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_remove),
                contentDescription = "icone menu",
                tint = FeaturedColor,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onDecreaseValue()
                    },
            )

            TextField(
                modifier = Modifier
                    .width(100.dp),
                value = value,
                onValueChange = { newValue ->
                    onValueChange(newValue)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = NormalColor,
                    unfocusedTextColor = NormalColor,
                    focusedIndicatorColor = BackgroundColor,
                    unfocusedIndicatorColor = BackgroundColor
                ),
                placeholder = {
                    Text(
                        text = "0",
                        modifier = Modifier.fillMaxWidth(),
                        color = BackgroundLight,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }
            )

            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "icone menu",
                tint = FeaturedColor,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onIncreaseValue()
                    },
            )
        }
    }
}

@Preview
@Composable
fun CreateExercisesScreenPreview() {
    val sharedViewModel: CreateSharedViewModel = viewModel()

    CreateExerciseScreen(
        navigate = {},
        viewModel = sharedViewModel
    )
}