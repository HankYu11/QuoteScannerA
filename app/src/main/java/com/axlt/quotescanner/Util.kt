package com.axlt.quotescanner

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.FileNotFoundException
import java.io.IOException

fun getBitmap(file: Uri, cr: ContentResolver): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val inputStream = cr.openInputStream(file)
        bitmap = BitmapFactory.decodeStream(inputStream)
        // close stream
        try {
            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    } catch (e: FileNotFoundException) {
    }
    return bitmap
}
