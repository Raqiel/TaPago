package com.example.tapago.data.images

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream

private const val MAX_DIMENSION_PX = 1080
private const val JPEG_QUALITY = 80

fun compressImage(context: Context, uri: Uri): ByteArray {
    val resolver = context.contentResolver

    val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, bounds) }
        ?: throw IllegalArgumentException("Não consegui abrir a imagem")

    val options = BitmapFactory.Options().apply {
        inSampleSize = calculateInSampleSize(bounds.outWidth, bounds.outHeight)
    }
    val bitmap = resolver.openInputStream(uri)?.use {
        BitmapFactory.decodeStream(it, null, options)
    } ?: throw IllegalArgumentException("Não consegui decodificar a imagem")

    return ByteArrayOutputStream().use { output ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, output)
        bitmap.recycle()
        output.toByteArray()
    }
}

private fun calculateInSampleSize(width: Int, height: Int): Int {
    var sampleSize = 1
    val maxSide = maxOf(width, height)
    while (maxSide / sampleSize > MAX_DIMENSION_PX) {
        sampleSize *= 2
    }
    return sampleSize
}
