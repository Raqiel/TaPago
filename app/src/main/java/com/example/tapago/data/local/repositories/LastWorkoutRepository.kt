package com.example.tapago.data.local.repositories

import android.content.Context
import com.example.tapago.models.Workout

data class LastWorkout(
    val letter: String,
    val name: String?,
    val startedAt: Long,
    val finished: Boolean
)

interface LastWorkoutRepository {
    fun get(): LastWorkout?
    fun markStarted(workout: Workout)
    fun markFinished()
}

class LastWorkoutRepositoryImpl(
    context: Context
) : LastWorkoutRepository {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun get(): LastWorkout? {
        val startedAt = prefs.getLong(KEY_STARTED_AT, 0L)
        if (startedAt == 0L) return null

        return LastWorkout(
            letter = prefs.getString(KEY_LETTER, null).orEmpty(),
            name = prefs.getString(KEY_NAME, null),
            startedAt = startedAt,
            finished = prefs.getBoolean(KEY_FINISHED, false)
        )
    }

    override fun markStarted(workout: Workout) {
        prefs.edit()
            .putString(KEY_LETTER, workout.letter)
            .putString(KEY_NAME, workout.name)
            .putLong(KEY_STARTED_AT, System.currentTimeMillis())
            .putBoolean(KEY_FINISHED, false)
            .apply()
    }

    override fun markFinished() {
        prefs.edit().putBoolean(KEY_FINISHED, true).apply()
    }

    private companion object {
        const val PREFS_NAME = "tapago_last_workout"
        const val KEY_LETTER = "letter"
        const val KEY_NAME = "name"
        const val KEY_STARTED_AT = "started_at"
        const val KEY_FINISHED = "finished"
    }
}
