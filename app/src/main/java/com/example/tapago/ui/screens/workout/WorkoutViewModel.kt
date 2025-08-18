package com.example.tapago.ui.screens.workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tapago.ui.screens.home.HomeUiState

class WorkoutViewModel(initialState: HomeUiState = HomeUiState()) : ViewModel() {
    var uiState by mutableStateOf(initialState)
}