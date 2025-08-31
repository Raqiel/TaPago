package com.example.tapago.ui.screens.create_exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tapago.Destination
import com.example.tapago.ui.components.EmbossedButton
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.Description
import com.example.tapago.ui.theme.FeaturedText

@Composable
fun CreateExercisesScreen(navigate: (Destination) -> Unit) {

    var letter by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var set by remember { mutableStateOf("") }
    var rep by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCardsColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Crie um treino",
            style = FeaturedText
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = "Letra",
            style = Description
        )
        TextField(
            value = letter,
            onValueChange = {
                letter = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Série",
            style = Description
        )
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Repetição",
            style = Description
        )
        TextField(
            value = description,
            onValueChange = {
                description = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Série",
            style = Description
        )
        TextField(
            value = set,
            onValueChange = {
                set = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Repetição",
            style = Description
        )
        TextField(
            value = rep,
            onValueChange = {
                rep = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Carga",
            style = Description
        )
        TextField(
            value = weight,
            onValueChange = {
                weight = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(50.dp))

        EmbossedButton() {
            Text(
                text = "Continuar",
                style = Description
            )
        }
    }
}

@Preview
@Composable
fun CreateExercisesScreenPreview() {
    CreateExercisesScreen(
        navigate = {}
    )
}