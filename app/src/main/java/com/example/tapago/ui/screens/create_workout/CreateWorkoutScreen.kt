package com.example.tapago.ui.screens.create_workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tapago.Destination
import com.example.tapago.R
import com.example.tapago.models.Workout
import com.example.tapago.models.mockWorkouts
import com.example.tapago.ui.components.EditNormalText
import com.example.tapago.ui.components.FeaturedText
import com.example.tapago.ui.components.ImageButton
import com.example.tapago.ui.components.LetterEditText
import com.example.tapago.ui.components.RoundButton
import com.example.tapago.ui.components.TextFieldView
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.LightColor
import com.example.tapago.ui.theme.NormalColor


@Composable
fun CreateWorkoutScreen(
    navigate: (Destination) -> Unit
) {

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
            LetterEditText(value = letter)
        }
        Spacer(modifier = Modifier.height(20.dp))
        ImageButton(
            color = LightColor,
            painter = Icons.Filled.Add
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldView(
            icon = null,
            value = name,
            placeholder = stringResource(R.string.create_workout_placeholder_name),
            error = false,
            onValueChange = { }
        )
        Spacer(modifier = Modifier.height(20.dp))
        EditNormalText(
            value = description,
            placeholder = stringResource(R.string.create_workout_placeholder_description),
            fontSize = null
        )
        Spacer(modifier = Modifier.weight(1f))
        RoundButton(
            color = NormalColor,
            imageVector = Icons.Filled.Check,
            onClick = {

            }
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun CreateWorkoutScreenPreview() {
    CreateWorkoutScreen(navigate = {})
}