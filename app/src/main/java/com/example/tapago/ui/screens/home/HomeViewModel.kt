package com.example.tapago.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel(initialState: HomeUiState = HomeUiState()) : ViewModel() {
    var uiState by mutableStateOf(initialState)
}