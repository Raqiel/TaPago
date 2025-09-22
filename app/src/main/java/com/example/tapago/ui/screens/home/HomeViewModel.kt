package com.example.tapago.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tapago.ui.screens.shared.data.WorkoutDao
import com.example.tapago.ui.screens.shared.ui.CreateSharedUiState
import com.example.tapago.usecase.GetWorkoutsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



class HomeViewModel(
    initialState: HomeUiState = HomeUiState(),
   private val getWorkoutsUseCase: GetWorkoutsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load(){
        viewModelScope.launch {
   getWorkoutsUseCase.invoke()?.collect {
            _uiState.value = _uiState.value.copy(workouts = it)
        }
        }
    }
}