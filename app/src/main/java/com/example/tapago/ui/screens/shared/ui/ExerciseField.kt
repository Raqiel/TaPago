package com.example.tapago.ui.screens.shared.ui

import com.example.tapago.models.Exercise
import java.math.BigDecimal

enum class ExerciseField {
    SET,
    REP,
    WEIGHT,
    BACKBOARD
}

fun Exercise.valueFor(field: ExerciseField): String? = when (field) {
    ExerciseField.SET -> set
    ExerciseField.REP -> rep
    ExerciseField.WEIGHT -> weight
    ExerciseField.BACKBOARD -> backboard
}

fun Exercise.withValue(field: ExerciseField, value: String): Exercise = when (field) {
    ExerciseField.SET -> copy(set = value)
    ExerciseField.REP -> copy(rep = value)
    ExerciseField.WEIGHT -> copy(weight = value)
    ExerciseField.BACKBOARD -> copy(backboard = value)
}

fun Exercise.step(field: ExerciseField, delta: BigDecimal): Exercise {
    val atual = valueFor(field)?.replace(',', '.')?.toBigDecimalOrNull() ?: BigDecimal.ZERO
    val proximo = (atual + delta).coerceIn(BigDecimal.ZERO, MAX_FIELD_VALUE)
    return withValue(field, proximo.stripTrailingZeros().toPlainString().replace('.', ','))
}

private val MAX_FIELD_VALUE = BigDecimal(999)
