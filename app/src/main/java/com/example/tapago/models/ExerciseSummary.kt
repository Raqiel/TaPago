package com.example.tapago.models

fun Exercise.resumo(): String = listOfNotNull(
    set?.takeIf { it.isNotBlank() }?.let { "$it séries" },
    rep?.takeIf { it.isNotBlank() }?.let { "$it reps" },
    weight?.takeIf { it.isNotBlank() }?.let { "$it kg" },
    backboard?.takeIf { it.isNotBlank() }?.let { "encosto $it" }
).joinToString("  ·  ")
