package com.example.tapago.models

data class Exercises(
    val id:String,
    val name:String?,
    val description:String?,
    val set:String?,
    val rep:String?,
    val weight:String?
)
val mockExercises = listOf<Exercises>(
    Exercises(
        id = "ex001",
        name = "Supino Reto com Barra",
        description = "Principal exercício para o desenvolvimento do peitoral maior, com auxílio do deltoide anterior e tríceps.",
        set = "4",
        rep = "8-10",
        weight = "80kg"
    ),
    Exercises(
        id = "ex002",
        name = "Agachamento Livre",
        description = "Exercício fundamental para membros inferiores, trabalhando quadríceps, glúteos e posteriores de coxa.",
        set = "3",
        rep = "12",
        weight = "60kg"
    ),
    Exercises(
        id = "ex003",
        name = "Puxada Frontal (Pulley)",
        description = "Focado no desenvolvimento do latíssimo do dorso (as 'asas'), contribuindo para costas mais largas.",
        set = "4",
        rep = "10",
        weight = "55kg"
    ),
    Exercises(
        id = "ex004",
        name = "Elevação Lateral com Halteres",
        description = "Exercício de isolamento para a cabeça medial dos ombros (deltoides), promovendo ombros mais largos.",
        set = "3",
        rep = "15",
        weight = "8kg"
    ),
    Exercises(
        id = "ex005",
        name = "Rosca Direta com Barra W",
        description = "Trabalho focado no bíceps braquial, um dos principais exercícios para o desenvolvimento dos braços.",
        set = "3",
        rep = "12",
        weight = null
    )
)
