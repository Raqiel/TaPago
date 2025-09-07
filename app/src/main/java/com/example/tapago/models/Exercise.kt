package com.example.tapago.models

data class Exercise(
    val id: String,
    val name: String?,
    val obs: String?,
    val set: String?,
    val rep: String?,
    val weight: String?,
    val backboard: String?
)

val mockExercises = listOf(
    Exercise(
        id = "ex001",
        name = "Supino Reto com Barra",
        obs = "Principal exercício para o desenvolvimento do peitoral maior, com auxílio do deltoide anterior e tríceps.",
        set = "4",
        rep = "8-10",
        weight = "80kg",
        backboard = "5"
    ),
    Exercise(
        id = "ex002",
        name = "Agachamento Livre",
        obs = "Exercício fundamental para membros inferiores, trabalhando quadríceps, glúteos e posteriores de coxa.",
        set = "3",
        rep = "12",
        weight = "60kg",
        backboard = "1"
    ),
    Exercise(
        id = "ex003",
        name = "Puxada Frontal (Pulley)",
        obs = "Focado no desenvolvimento do latíssimo do dorso (as 'asas'), contribuindo para costas mais largas.",
        set = "4",
        rep = "10",
        weight = "55kg",
        backboard = "2"
    ),
    Exercise(
        id = "ex004",
        name = "Elevação Lateral com Halteres",
        obs = "Exercício de isolamento para a cabeça medial dos ombros (deltoides), promovendo ombros mais largos.",
        set = "3",
        rep = "15",
        weight = "8kg",
        backboard = "4"
    ),
    Exercise(
        id = "ex005",
        name = "Rosca Direta com Barra W",
        obs = "Trabalho focado no bíceps braquial, um dos principais exercícios para o desenvolvimento dos braços.",
        set = "3",
        rep = "12",
        weight = null,
        backboard = "3"
    )
)
