package com.example.datasetmanagement.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

fun saveBitmapAndGetUri(context: Context, bitmap: Bitmap): Uri? {
    val file = File(context.cacheDir, UUID.randomUUID().toString() + ".jpg")
    try {
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return Uri.fromFile(file)
}
