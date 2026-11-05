package com.example.tapago.ui.screens.exercise_session

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tapago.Destination
import com.example.tapago.R
import com.example.tapago.data.local.repositories.LastWorkoutRepository
import com.example.tapago.data.local.repositories.WorkoutRepository
import com.example.tapago.models.Exercise
import com.example.tapago.models.Workout
import com.example.tapago.models.mockExercises
import com.example.tapago.ui.components.FeaturedText
import com.example.tapago.ui.screens.shared.ui.ExerciseField
import com.example.tapago.ui.screens.shared.ui.step
import com.example.tapago.ui.components.MarombaButton
import com.example.tapago.ui.components.MarombaSecondButton
import com.example.tapago.ui.components.SmallText
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.BackgroundLight
import com.example.tapago.ui.theme.FeaturedColor
import com.example.tapago.ui.theme.LightColor
import com.example.tapago.ui.theme.NewYellow
import com.example.tapago.ui.theme.Oswald
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import org.koin.compose.koinInject
import java.util.Locale

private const val REST_SECONDS = 50

@Composable
fun ExerciseSessionScreen(
    workout: Workout?,
    navigate: (Destination) -> Unit
) {
    val lastWorkoutRepository: LastWorkoutRepository = koinInject()
    val workoutRepository: WorkoutRepository = koinInject()
    val scope = rememberCoroutineScope()

    var exercises by rememberSaveable { mutableStateOf(workout?.exercises.orEmpty()) }
    var order by rememberSaveable {
        mutableStateOf(exercises.indices.filterNot { exercises[it].isBlank() })
    }
    var position by rememberSaveable { mutableIntStateOf(0) }
    var skipped by rememberSaveable { mutableStateOf(emptyList<Int>()) }
    var completedSets by rememberSaveable { mutableStateOf(List(exercises.size) { 0 }) }
    var secondsLeft by rememberSaveable { mutableIntStateOf(REST_SECONDS) }
    var isResting by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(workout) {
        if (workout != null) {
            lastWorkoutRepository.markStarted(workout)
        }
    }

    if (order.isEmpty()) {
        EmptySession(onBack = { navigate(Destination.Home()) })
        return
    }

    LaunchedEffect(position, isResting) {
        while (isResting && secondsLeft > 0) {
            delay(1_000L)
            secondsLeft -= 1
        }
        if (secondsLeft == 0) {
            isResting = false
        }
    }

    val safePosition = position.coerceIn(0, order.lastIndex)
    val exerciseIndex = order.getOrElse(safePosition) { 0 }
    val isLast = safePosition == order.lastIndex
    val isFirst = safePosition == 0

    fun resetRest() {
        secondsLeft = REST_SECONDS
        isResting = false
    }

    fun ajustar(campo: ExerciseField, delta: BigDecimal) {
        val novaLista = exercises.toMutableList().also {
            it[exerciseIndex] = it[exerciseIndex].step(campo, delta)
        }
        exercises = novaLista

        val base = workout ?: return
        scope.launch {
            workoutRepository.insertWorkout(base.copy(exercises = novaLista))
        }
    }

    ExerciseSessionContent(
        exercise = exercises[exerciseIndex],
        position = safePosition + 1,
        total = order.size,
        secondsLeft = secondsLeft,
        isResting = isResting,
        isLast = isLast,
        isFirst = isFirst,
        foiPulado = exerciseIndex in skipped,
        podePular = !isLast,
        pendentesPulados = skipped.size,
        setsDone = completedSets.getOrElse(exerciseIndex) { 0 },
        onFinishedSet = {
            completedSets = completedSets.toMutableList().also {
                it[exerciseIndex] = it[exerciseIndex] + 1
            }
            skipped = skipped - exerciseIndex
            secondsLeft = REST_SECONDS
            isResting = true
        },
        onToggleRest = { isResting = !isResting },
        onUndoSet = {
            completedSets = completedSets.toMutableList().also {
                it[exerciseIndex] = (it[exerciseIndex] - 1).coerceAtLeast(0)
            }
        },
        onSkip = {
            order = order.toMutableList().also {
                it.removeAt(safePosition)
                it.add(exerciseIndex)
            }
            if (exerciseIndex !in skipped) {
                skipped = skipped + exerciseIndex
            }
            resetRest()
        },
        onPrevious = {
            position = safePosition - 1
            resetRest()
        },
        onNextExercise = {
            position = safePosition + 1
            resetRest()
        },
        onResetTimer = { secondsLeft = REST_SECONDS },
        onIncrease = { campo -> ajustar(campo, BigDecimal.ONE) },
        onDecrease = { campo -> ajustar(campo, BigDecimal.ONE.negate()) },
        onFinish = {
            lastWorkoutRepository.markFinished()
            navigate(Destination.Home())
        }
    )
}

@Composable
private fun ExerciseSessionContent(
    exercise: Exercise,
    position: Int,
    total: Int,
    secondsLeft: Int,
    isResting: Boolean,
    isLast: Boolean,
    isFirst: Boolean,
    foiPulado: Boolean,
    podePular: Boolean,
    pendentesPulados: Int,
    setsDone: Int,
    onFinishedSet: () -> Unit,
    onToggleRest: () -> Unit,
    onUndoSet: () -> Unit,
    onSkip: () -> Unit,
    onPrevious: () -> Unit,
    onNextExercise: () -> Unit,
    onResetTimer: () -> Unit,
    onIncrease: (ExerciseField) -> Unit,
    onDecrease: (ExerciseField) -> Unit,
    onFinish: () -> Unit
) {
    val targetSets = exercise.set?.trim()?.toIntOrNull()
    val todasFeitas = targetSets != null && setsDone >= targetSets

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))

        SmallText(
            text = "Exercício $position de $total",
            size = 14,
            color = NewYellow
        )

        if (foiPulado) {
            Spacer(Modifier.height(6.dp))
            SmallText(
                text = "Você deixou esse pra depois",
                size = 13,
                color = FeaturedColor
            )
        }

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(4.dp, NewYellow, CircleShape)
        ) {
            if (exercise.photo != null) {
                AsyncImage(
                    model = exercise.photo,
                    contentDescription = exercise.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.img_exercise),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        FeaturedText(
            text = exercise.name.orEmpty().ifBlank { "Exercício sem nome" },
            size = 26
        )

        Spacer(Modifier.height(20.dp))

        MetricRows(
            exercise = exercise,
            onIncrease = onIncrease,
            onDecrease = onDecrease
        )

        Spacer(Modifier.height(24.dp))

        SetCounter(
            setsDone = setsDone,
            targetSets = targetSets,
            todasFeitas = todasFeitas,
            onUndoSet = onUndoSet
        )

        Spacer(Modifier.height(20.dp))

        RestDial(
            secondsLeft = secondsLeft,
            isResting = isResting,
            onToggleRest = onToggleRest
        )

        Spacer(Modifier.height(20.dp))

        PlayerControls(
            podeVoltar = !isFirst,
            podeAvancar = !isLast,
            onPrevious = onPrevious,
            onResetTimer = onResetTimer,
            onNextExercise = onNextExercise
        )

        Spacer(Modifier.height(24.dp))

        MarombaButton(
            textButton = "Fiz a série",
            iconButton = null,
            onClick = onFinishedSet
        )

        Spacer(Modifier.height(12.dp))

        MarombaSecondButton(
            textButton = "Finalizar treino",
            iconButton = null,
            onClick = onFinish
        )

        if (pendentesPulados > 0) {
            Spacer(Modifier.height(8.dp))
            SmallText(
                text = "Ainda tem $pendentesPulados exercício pulado sem terminar",
                size = 12,
                color = FeaturedColor
            )
        }

        Spacer(Modifier.height(16.dp))

        if (podePular) {
            Text(
                text = "Aparelho ocupado, deixar pra depois",
                color = FeaturedColor,
                fontSize = 16.sp,
                fontFamily = Oswald,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onSkip() }
            )
        }

        if (!exercise.obs.isNullOrBlank()) {
            Spacer(Modifier.height(20.dp))
            SmallText(
                text = exercise.obs,
                size = 13
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun PlayerControls(
    podeVoltar: Boolean,
    podeAvancar: Boolean,
    onPrevious: () -> Unit,
    onResetTimer: () -> Unit,
    onNextExercise: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ControlButton(
            icon = Icons.Filled.SkipPrevious,
            descricao = "Exercício anterior",
            enabled = podeVoltar,
            onClick = onPrevious
        )
        Spacer(Modifier.width(20.dp))
        ControlButton(
            icon = Icons.Filled.Replay,
            descricao = "Zerar o descanso",
            enabled = true,
            onClick = onResetTimer
        )
        Spacer(Modifier.width(20.dp))
        ControlButton(
            icon = Icons.Filled.SkipNext,
            descricao = "Próximo exercício",
            enabled = podeAvancar,
            onClick = onNextExercise
        )
    }
}

@Composable
private fun ControlButton(
    icon: ImageVector,
    descricao: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(BackgroundCardsColor)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = descricao,
            tint = if (enabled) NewYellow else BackgroundLight,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
private fun MetricRows(
    exercise: Exercise,
    onIncrease: (ExerciseField) -> Unit,
    onDecrease: (ExerciseField) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BackgroundCardsColor)
            .padding(vertical = 8.dp)
    ) {
        MetricRow(
            rotulo = "Repetições",
            valor = exercise.rep,
            campo = ExerciseField.REP,
            onIncrease = onIncrease,
            onDecrease = onDecrease
        )
        MetricRow(
            rotulo = "Peso (kg)",
            valor = exercise.weight,
            campo = ExerciseField.WEIGHT,
            onIncrease = onIncrease,
            onDecrease = onDecrease
        )
        if (!exercise.backboard.isNullOrBlank()) {
            MetricRow(
                rotulo = "Encosto",
                valor = exercise.backboard,
                campo = ExerciseField.BACKBOARD,
                onIncrease = onIncrease,
                onDecrease = onDecrease
            )
        }
    }
}

@Composable
private fun MetricRow(
    rotulo: String,
    valor: String?,
    campo: ExerciseField,
    onIncrease: (ExerciseField) -> Unit,
    onDecrease: (ExerciseField) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallText(
            text = rotulo,
            size = 14
        )

        Spacer(Modifier.weight(1f))

        StepButton(
            icon = Icons.Filled.Remove,
            descricao = "Diminuir $rotulo",
            onClick = { onDecrease(campo) }
        )

        Text(
            text = valor?.takeIf { it.isNotBlank() } ?: "0",
            color = FeaturedColor,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = Oswald,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(72.dp)
        )

        StepButton(
            icon = Icons.Filled.Add,
            descricao = "Aumentar $rotulo",
            onClick = { onIncrease(campo) }
        )
    }
}

@Composable
private fun StepButton(
    icon: ImageVector,
    descricao: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(BackgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = descricao,
            tint = NewYellow,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun SetCounter(
    setsDone: Int,
    targetSets: Int?,
    todasFeitas: Boolean,
    onUndoSet: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = if (targetSets != null) {
                "Série $setsDone de $targetSets"
            } else {
                "Séries feitas: $setsDone"
            },
            color = if (todasFeitas) FeaturedColor else LightColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = Oswald,
            textAlign = TextAlign.Center
        )

        if (todasFeitas) {
            SmallText(
                text = "Todas as séries feitas",
                size = 12,
                color = FeaturedColor
            )
        }

        if (setsDone > 0) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "desfazer série",
                color = NewYellow,
                fontSize = 12.sp,
                fontFamily = Oswald,
                modifier = Modifier.clickable { onUndoSet() }
            )
        }
    }
}

@Composable
private fun RestDial(
    secondsLeft: Int,
    isResting: Boolean,
    onToggleRest: () -> Unit
) {
    val acabou = secondsLeft == 0

    Box(
        modifier = Modifier
            .size(170.dp)
            .clickable(enabled = !acabou) { onToggleRest() },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { secondsLeft.toFloat() / REST_SECONDS.toFloat() },
            modifier = Modifier.fillMaxSize(),
            color = if (acabou) FeaturedColor else NewYellow,
            trackColor = BackgroundCardsColor,
            strokeWidth = 10.dp
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = formatSeconds(secondsLeft),
                color = if (acabou) FeaturedColor else LightColor,
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = Oswald,
                textAlign = TextAlign.Center
            )
            SmallText(
                text = when {
                    acabou -> "descanso acabou"
                    isResting -> "toque para pausar"
                    secondsLeft < REST_SECONDS -> "pausado"
                    else -> "descanso"
                },
                size = 11
            )
        }
    }
}

@Composable
private fun EmptySession(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FeaturedText(
            text = "Esse treino não tem exercícios",
            size = 24
        )
        Spacer(Modifier.height(24.dp))
        MarombaSecondButton(
            textButton = "Voltar",
            iconButton = null,
            onClick = onBack
        )
    }
}

private fun formatSeconds(seconds: Int): String {
    val minutos = seconds / 60
    val restante = seconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutos, restante)
}

@Preview
@Composable
fun ExerciseSessionScreenPreview() {
    ExerciseSessionContent(
        exercise = mockExercises.first(),
        position = 2,
        total = 5,
        secondsLeft = 38,
        isResting = true,
        isLast = false,
        isFirst = false,
        foiPulado = true,
        podePular = true,
        pendentesPulados = 1,
        setsDone = 1,
        onFinishedSet = {},
        onToggleRest = {},
        onUndoSet = {},
        onSkip = {},
        onPrevious = {},
        onNextExercise = {},
        onIncrease = {},
        onDecrease = {},
        onResetTimer = {},
        onFinish = {}
    )
}
