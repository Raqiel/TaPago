package com.example.tapago.ui.screens.create_workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tapago.Destination
import com.example.tapago.R
import com.example.tapago.ui.components.EditNormalText
import com.example.tapago.ui.components.FeaturedText
import com.example.tapago.ui.components.ImageButton
import com.example.tapago.ui.components.LetterEditText
import com.example.tapago.ui.components.RoundButton
import com.example.tapago.ui.components.TextFieldView
import com.example.tapago.ui.screens.shared.CreateSharedViewModel
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.LightColor
import com.example.tapago.ui.theme.NormalColor


@Composable
fun CreateWorkoutScreen(
    navigate: (Destination) -> Unit,
    viewModel: CreateSharedViewModel
) {

    val uiState by viewModel.uiState.collectAsState()
    var letter = remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var description = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FeaturedText(
            text = "Crie um treino",
            size = 20
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LetterEditText(
                value = uiState.letter ?: "",
                onValueChange = { viewModel.onLetterChanged(it) })
        }
        Spacer(modifier = Modifier.height(20.dp))
        ImageButton(
            color = LightColor,
            painter = Icons.Outlined.Add
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldView(
            icon = null,
            value = uiState.workoutName ?: "",
            placeholder = stringResource(R.string.create_workout_placeholder_name),
            error = false,
            onValueChange = { viewModel.onWorkoutNameChanged(it) }
        )
        Spacer(modifier = Modifier.height(20.dp))
        EditNormalText(
            value = uiState.description ?: "",
            placeholder = stringResource(R.string.create_workout_placeholder_description),
            fontSize = null,
            onValueChange = { viewModel.onDescriptionChanged(it) }
        )
        Spacer(modifier = Modifier.weight(1f))
        RoundButton(
            color = NormalColor,
            imageVector = Icons.Filled.Check,
            onClick = {
                navigate(Destination.CreateExercise())

            }
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun CreateWorkoutScreenPreview() {
    val sharedViewModel: CreateSharedViewModel = viewModel()
    CreateWorkoutScreen(navigate = {}, viewModel = sharedViewModel)
}