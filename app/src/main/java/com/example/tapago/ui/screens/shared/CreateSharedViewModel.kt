package com.example.tapago.ui.screens.shared

import androidx.lifecycle.ViewModel
import com.example.tapago.models.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class CreateSharedViewModel(
    initialState: CreateSharedUiState = CreateSharedUiState(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<CreateSharedUiState> = _uiState.asStateFlow()

    fun onWorkoutNameChanged(workoutName: String) {
        _uiState.value = _uiState.value.copy(workoutName = workoutName)
    }

    fun onLetterChanged(workoutLetter: String) {
        _uiState.value = _uiState.value.copy(letter = workoutLetter)
    }

    fun onDescriptionChanged(workoutDescription: String) {
        _uiState.value = _uiState.value.copy(description = workoutDescription)
    }

    private fun updateExerciseAtIndex(index: Int, updater: (exercise: Exercise) -> Exercise) {
        val originalExercises = _uiState.value.exercises
        if (originalExercises.isNullOrEmpty() || index < 0 || index >= originalExercises.size) {
            return
        }
        val currentExercises = originalExercises.toMutableList()
        val exerciseToUpdate = currentExercises[index]
        val updatedExercise = updater(exerciseToUpdate)
        currentExercises[index] = updatedExercise
        _uiState.value = _uiState.value.copy(exercises = currentExercises)
    }

    fun onExerciseNameChanged(index: Int) {
        updateExerciseAtIndex(index) { exercise ->
            exercise.copy(name = uiState.value.exercises?.get(index)?.name)
        }
    }
    fun onObsChanged(index: Int) {
        updateExerciseAtIndex(index) { exercise ->
            exercise.copy(obs = uiState.value.exercises?.get(index)?.obs)
        }
    }

    fun onSetChanged(index: Int) {
        updateExerciseAtIndex(index) { exercise ->
            exercise.copy(set = uiState.value.exercises?.get(index)?.set)
        }
    }

    fun onRepChanged(index: Int) {
        updateExerciseAtIndex(index) { exercise ->
            exercise.copy(rep = uiState.value.exercises?.get(index)?.rep)
        }
    }

    fun onWeightChanged(index: Int) {
        updateExerciseAtIndex(index) { exercise ->
            exercise.copy(weight = uiState.value.exercises?.get(index)?.weight)
        }
    }

    fun onBackBoardChanged(index: Int) {
        updateExerciseAtIndex(index) { exercise ->
            exercise.copy(backboard = uiState.value.exercises?.get(index)?.backboard)
        }
    }

    fun onIncreaseSet(index:Int) {
        val originalExercises = _uiState.value.exercises
        if (originalExercises.isNullOrEmpty() || index < 0 || index >= originalExercises.size) {
            return
        }
        val currentExercises = originalExercises.toMutableList()
        val exerciseToUpdate = currentExercises[index]
        val currentSetString = exerciseToUpdate.set?.ifEmpty { "0" }
        var setToInt = currentSetString?.toIntOrNull() ?: 0
        val newSet = setToInt+ 1
        val updatedExercise = exerciseToUpdate.copy(set = newSet.toString())
        currentExercises[index] = updatedExercise
        _uiState.value = _uiState.value.copy(exercises = currentExercises)
    }

    fun onIncreaseRep(index: Int) {
        val originalExercises = _uiState.value.exercises
        if (originalExercises.isNullOrEmpty() || index < 0 || index >= originalExercises.size) {
            return
        }

        val currentExercises = originalExercises.toMutableList()
        val exerciseToUpdate = currentExercises[index]

        val currentRepString = exerciseToUpdate.rep?.ifEmpty { "0" }
        val repToInt = currentRepString?.toIntOrNull() ?: 0

        val newRep = repToInt + 1

        val updatedExercise = exerciseToUpdate.copy(rep = newRep.toString())
        currentExercises[index] = updatedExercise

        _uiState.value = _uiState.value.copy(exercises = currentExercises)
    }

    fun onIncreaseWeight(index: Int) {
        val originalExercises = _uiState.value.exercises
        if (originalExercises.isNullOrEmpty() || index < 0 || index >= originalExercises.size) {
            return
        }

        val currentExercises = originalExercises.toMutableList()
        val exerciseToUpdate = currentExercises[index]

        val currentWeightString = exerciseToUpdate.weight?.ifEmpty { "0" }
        val weightToInt = currentWeightString?.toIntOrNull() ?: 0

        val newWeight = weightToInt + 1

        val updatedExercise = exerciseToUpdate.copy(weight = newWeight.toString())
        currentExercises[index] = updatedExercise

        _uiState.value = _uiState.value.copy(exercises = currentExercises)
    }

    fun onIncreaseBackBoard(index: Int) {
        val originalExercises = _uiState.value.exercises
        if (originalExercises.isNullOrEmpty() || index < 0 || index >= originalExercises.size) {
            return
        }

        val currentExercises = originalExercises.toMutableList()
        val exerciseToUpdate = currentExercises[index]

        val currentBackBoardString = exerciseToUpdate.backboard?.ifEmpty { "0" }
        var backBoardToInt = currentBackBoardString?.toIntOrNull() ?: 0

        val newBackBoard = ++backBoardToInt

        val updatedExercise = exerciseToUpdate.copy(backboard = newBackBoard.toString())
        currentExercises[index] = updatedExercise

        _uiState.value = _uiState.value.copy(exercises = currentExercises)
    }

    fun onDecreaseSet(index: Int) {
        val originalExercises = _uiState.value.exercises
        if (originalExercises.isNullOrEmpty() || index < 0 || index >= originalExercises.size) {
            return
        }

        val currentExercises = originalExercises.toMutableList()
        val exerciseToUpdate = currentExercises[index]

        val currentSetString = exerciseToUpdate.set?.ifEmpty { "0" }
        val setToInt = currentSetString?.toIntOrNull() ?: 0

        val newSet = if (setToInt > 0) setToInt - 1 else 0

        val updatedExercise = exerciseToUpdate.copy(set = newSet.toString())
        currentExercises[index] = updatedExercise

        _uiState.value = _uiState.value.copy(exercises = currentExercises)
    }

    fun onDecreaseRep(index: Int) {
        val originalExercises = _uiState.value.exercises
        if (originalExercises.isNullOrEmpty() || index < 0 || index >= originalExercises.size) {
            return
        }

        val currentExercises = originalExercises.toMutableList()
        val exerciseToUpdate = currentExercises[index]

        val currentRepString = exerciseToUpdate.rep?.ifEmpty { "0" }
        val repToInt = currentRepString?.toIntOrNull() ?: 0

        val newRep = if (repToInt > 0) repToInt - 1 else 0

        val updatedExercise = exerciseToUpdate.copy(rep = newRep.toString())
        currentExercises[index] = updatedExercise

        _uiState.value = _uiState.value.copy(exercises = currentExercises)
    }

    fun onDecreaseWeight(index: Int) {
        val originalExercises = _uiState.value.exercises
        if (originalExercises.isNullOrEmpty() || index < 0 || index >= originalExercises.size) {
            return
        }

        val currentExercises = originalExercises.toMutableList()
        val exerciseToUpdate = currentExercises[index]

        val currentWeightString = exerciseToUpdate.weight?.ifEmpty { "0" }
        val weightToInt = currentWeightString?.toIntOrNull() ?: 0

        val newWeight = if (weightToInt > 0) weightToInt - 1 else 0

        val updatedExercise = exerciseToUpdate.copy(weight = newWeight.toString())
        currentExercises[index] = updatedExercise

        _uiState.value = _uiState.value.copy(exercises = currentExercises)
    }

    fun onDecreaseBackBoard(index: Int) {
        val originalExercises = _uiState.value.exercises
        if (originalExercises.isNullOrEmpty() || index < 0 || index >= originalExercises.size) {
            return
        }

        val currentExercises = originalExercises.toMutableList()
        val exerciseToUpdate = currentExercises[index]

        val currentBackBoardString = exerciseToUpdate.backboard?.ifEmpty { "0" }
        val backBoardToInt = currentBackBoardString?.toIntOrNull() ?: 0

        val newBackBoard = if (backBoardToInt > 0) backBoardToInt - 1 else 0

        val updatedExercise = exerciseToUpdate.copy(backboard = newBackBoard.toString())
        currentExercises[index] = updatedExercise

        _uiState.value = _uiState.value.copy(exercises = currentExercises)
    }

    fun addNewExercise() {
        val newExercise = Exercise(
            id = UUID.randomUUID().toString(),
            name = "",
            obs = "",
            set = "",
            rep = "",
            weight = "",
            backboard = ""
        )
        val currentExercises = _uiState.value.exercises ?: emptyList()
        _uiState.value = _uiState.value.copy(
            exercises = currentExercises + newExercise
        )
    }
}