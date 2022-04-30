package com.sk.unsplash.repository

import android.graphics.Bitmap
import android.os.Environment
import com.sk.unsplash.models.photo.PhotoResponseItem
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object LocalDataRepository : ILocalDataRepository {

    override suspend fun savePhoto(
        image: Bitmap,
        photoResponseItem: PhotoResponseItem?,
        listener: (Boolean) -> Unit
    ) {
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