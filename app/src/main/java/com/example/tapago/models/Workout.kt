package com.example.tapago.models

data class Workout (
    val id:String,
    val letter:String,
    val name:String?,
    val description:String?,
    val photo:String?,
    val exercises:List<Exercises>?
)

val mockWorkouts = listOf(
    Workout(
        id = "wk001",
        letter = "A",
        name = "Peito e Ombros",
        description = "Treino focado no desenvolvimento do peitoral e deltoides, para um tronco mais forte e imponente.",
        photo = "https://example.com/photo_A.jpg",
        exercises = listOf(
            mockExercises[0],
            mockExercises[3]
        )
    ),
    Workout(
        id = "wk002",
        letter = "B",
        name = "Pernas",
        description = "Treino completo para membros inferiores, essencial para uma base sólida e equilíbrio corporal.",
        photo = "https://example.com/photo_B.jpg",
        exercises = listOf(
            mockExercises[1]
        )
    ),
    Workout(
        id = "wk003",
        letter = "C",
        name = "Costas e Bíceps",
        description = "Foco em puxadas para construir costas largas e fortes, com trabalho complementar para os braços.",
        photo = "https://example.com/photo_C.jpg",
        exercises = listOf(
            mockExercises[2],
            mockExercises[4]
        )
    ),
    Workout(
        id = "wk004",
        letter = "D",
        name = "Full Body - Força",
        description = "Um treino que abrange os principais grupos musculares, ideal para quem busca ganhos de força geral.",
        photo = "https://example.com/photo_D.jpg",
        exercises = listOf(
            mockExercises[0],
            mockExercises[1],
            mockExercises[2]
        )
    ),
    Workout(
        id = "wk005",
        letter = "E",
        name = "Upper Body - Hipertrofia",
        description = "Treino para a parte superior do corpo com foco em hipertrofia, buscando o aumento de massa muscular.",
        photo = "https://example.com/photo_E.jpg",
        exercises = listOf(
            mockExercises[0],
            mockExercises[2],
            mockExercises[3],
            mockExercises[4]
        )
    )
)