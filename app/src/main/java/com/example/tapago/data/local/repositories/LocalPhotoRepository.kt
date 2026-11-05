package com.example.tapago.data.local.repositories

import android.content.Context
import android.net.Uri
import com.example.tapago.data.images.compressImage
import com.example.tapago.data.remote.repositories.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class LocalPhotoRepository(
    private val appContext: Context
) : PhotoRepository {

    override suspend fun uploadWorkoutPhoto(workoutId: String, imageUri: Uri): Result<String> =
        savePhoto("workout_$workoutId", imageUri)

    override suspend fun uploadExercisePhoto(exerciseId: String, imageUri: Uri): Result<String> =
        savePhoto("exercise_$exerciseId", imageUri)

    override suspend fun deleteWorkoutPhoto(downloadUrl: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val file = downloadUrl.toFile()
                if (file != null && file.exists()) file.delete()
                Unit
            }
        }

    private suspend fun savePhoto(fileName: String, imageUri: Uri): Result<String> =
        withContext(Dispatchers.IO) {
            runCatching {
                val directory = File(appContext.filesDir, PHOTOS_DIRECTORY).apply { mkdirs() }
                val file = File(directory, "$fileName.jpg")
                file.writeBytes(compressImage(appContext, imageUri))
                Uri.fromFile(file).toString()
            }
        }

    private fun String.toFile(): File? = runCatching { Uri.parse(this).path?.let(::File) }.getOrNull()

    private companion object {
        const val PHOTOS_DIRECTORY = "photos"
    }
}
