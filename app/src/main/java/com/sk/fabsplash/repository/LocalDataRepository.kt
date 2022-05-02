package com.sk.fabsplash.repository

import android.graphics.Bitmap
import android.os.Environment
import com.sk.fabsplash.models.photo.PhotoResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object LocalDataRepository : ILocalDataRepository {

    override suspend fun savePhoto(
        image: Bitmap,
        photoResponseItem: PhotoResponseItem?,
        listener: (Boolean) -> Unit
    )= withContext(Dispatchers.IO) {
        val imageFileName = "JPEG_" + photoResponseItem?.id + ".jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/Unsplash"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            listener(true)
        }
    }

}