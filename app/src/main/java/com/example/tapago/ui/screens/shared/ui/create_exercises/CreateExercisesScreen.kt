package com.example.tapago.ui.screens.shared.ui.create_exercises

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tapago.Destination
import com.example.tapago.R
import com.example.tapago.models.Exercise
import com.example.tapago.models.mockExercises
import com.example.tapago.ui.components.EditNormalText
import com.example.tapago.ui.components.FeaturedText
import com.example.tapago.ui.components.MarombaButton
import com.example.tapago.ui.components.MarombaSecondButton
import com.example.tapago.ui.screens.shared.ui.CreateSharedUiState
import com.example.tapago.ui.screens.shared.ui.CreateSharedViewModel
import com.example.tapago.ui.screens.shared.ui.ExerciseField
import com.example.tapago.ui.screens.shared.ui.valueFor
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.BackgroundLight
import com.example.tapago.ui.theme.ErrorColor
import com.example.tapago.ui.theme.FeaturedColor
import com.example.tapago.ui.theme.MediumColor
import com.example.tapago.ui.theme.NormalColor
import com.example.tapago.ui.theme.RobotoRegular
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateExerciseScreen(
    navigate: (Destination) -> Unit,
    viewModel: CreateSharedViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var photoExerciseId by rememberSaveable { mutableStateOf<String?>(null) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        photoExerciseId?.let { viewModel.onExercisePhotoSelected(it, uri) }
        photoExerciseId = null
    }

    CreateExerciseContent(
        uiState = uiState,
        onNameChange = viewModel::onExerciseNameChanged,
        onObsChange = viewModel::onObsChanged,
        onFieldChange = viewModel::onFieldChanged,
        onIncrease = viewModel::onIncrease,
        onDecrease = viewModel::onDecrease,
        onPickPhoto = { exerciseId ->
            photoExerciseId = exerciseId
            photoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onRemove = viewModel::removeExercise,
        onAddExercise = viewModel::addNewExercise,
        onSave = { viewModel.onSaveWorkout(navigate) }
    )
}

@Composable
fun CreateExerciseContent(
    uiState: CreateSharedUiState,
    onNameChange: (String, String) -> Unit,
    onObsChange: (String, String) -> Unit,
    onFieldChange: (String, ExerciseField, String) -> Unit,
    onIncrease: (String, ExerciseField) -> Unit,
    onDecrease: (String, ExerciseField) -> Unit,
    onPickPhoto: (String) -> Unit,
    onRemove: (String) -> Unit,
    onAddExercise: () -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
            items(
                items = uiState.exercises.orEmpty(),
                key = { it.id }
            ) { exercise ->
                ExerciseCardView(
                    exercise = exercise,
                    onNameChange = { onNameChange(exercise.id, it) },
                    onObsChange = { onObsChange(exercise.id, it) },
                    onFieldChange = { field, value -> onFieldChange(exercise.id, field, value) },
                    onIncrease = { onIncrease(exercise.id, it) },
                    onDecrease = { onDecrease(exercise.id, it) },
                    onPickPhoto = { onPickPhoto(exercise.id) },
                    onRemove = { onRemove(exercise.id) }
                )

                Spacer(modifier = Modifier.height(30.dp))
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    uiState.errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = ErrorColor,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = RobotoRegular,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    if (uiState.isSaving) {
                        CircularProgressIndicator(color = FeaturedColor)
                    } else {
                        MarombaSecondButton(
                            textButton = null,
                            iconButton = Icons.Filled.Add,
                            onClick = onAddExercise
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        MarombaButton(
                            textButton = null,
                            iconButton = Icons.Filled.Check,
                            onClick = onSave
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun ExerciseCardView(
    exercise: Exercise,
    onNameChange: (String) -> Unit,
    onObsChange: (String) -> Unit,
    onFieldChange: (ExerciseField, String) -> Unit,
    onIncrease: (ExerciseField) -> Unit,
    onDecrease: (ExerciseField) -> Unit,
    onPickPhoto: () -> Unit,
    onRemove: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remover exercício",
                    tint = FeaturedColor
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            ExercisePhotoView(
                photo = exercise.photo,
                onClick = onPickPhoto,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            TextField(
                modifier = Modifier.weight(1f),
                value = exercise.name.orEmpty(),
                onValueChange = onNameChange,
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
                        text = "Nome do exercício",
                        color = BackgroundLight,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
            )
        }

        ExerciseField.entries.chunked(2).forEach { rowFields ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowFields.forEach { field ->
                    NumberRefView(
                        modifier = Modifier.weight(1f),
                        title = field.label,
                        value = exercise.valueFor(field).orEmpty(),
                        keyboardType = field.keyboardType,
                        onValueChange = { onFieldChange(field, it) },
                        onDecrease = { onDecrease(field) },
                        onIncrease = { onIncrease(field) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        EditNormalText(
            value = exercise.obs.orEmpty(),
            placeholder = "Descrição do exercício",
            fontSize = null,
            onValueChange = onObsChange
        )
    }
}

@Composable
fun ExercisePhotoView(
    photo: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(150.dp)
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (photo != null) {
            AsyncImage(
                model = photo,
                contentDescription = "Foto do exercício",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.add_photo),
                contentDescription = "Adicionar foto do exercício",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun NumberRefView(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = title,
            color = MediumColor,
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
            IconButton(onClick = onDecrease) {
                Icon(
                    painter = painterResource(R.drawable.ic_remove),
                    contentDescription = "Diminuir $title",
                    tint = FeaturedColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            BasicTextField(
                modifier = Modifier.weight(1f),
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = NormalColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = ImeAction.Next
                ),
                cursorBrush = SolidColor(FeaturedColor),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = "0",
                                color = BackgroundLight,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )

            IconButton(onClick = onIncrease) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Aumentar $title",
                    tint = FeaturedColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

private val ExerciseField.label: String
    get() = when (this) {
        ExerciseField.SET -> "Série"
        ExerciseField.REP -> "Repetição"
        ExerciseField.WEIGHT -> "Peso"
        ExerciseField.BACKBOARD -> "Encosto"
    }

private val ExerciseField.keyboardType: KeyboardType
    get() = if (this == ExerciseField.WEIGHT) KeyboardType.Decimal else KeyboardType.Number

@Preview
@Composable
fun CreateExercisesScreenPreview() {
    CreateExerciseContent(
        uiState = CreateSharedUiState(exercises = mockExercises.take(2)),
        onNameChange = { _, _ -> },
        onObsChange = { _, _ -> },
        onFieldChange = { _, _, _ -> },
        onIncrease = { _, _ -> },
        onDecrease = { _, _ -> },
        onPickPhoto = {},
        onRemove = {},
        onAddExercise = {},
        onSave = {}
    )
}
