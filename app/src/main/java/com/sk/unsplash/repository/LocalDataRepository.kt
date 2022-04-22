package com.sk.unsplash.repository

import android.graphics.Bitmap
import android.os.Environment
import com.sk.unsplash.api.UnsplashApi
import com.sk.unsplash.models.photo.PhotoResponse
import com.sk.unsplash.models.photo.PhotoResponseItem
import com.sk.unsplash.models.searchPhoto.SearchPhotoResponse
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object LocalDataRepository:ILocalDataRepository {

    private const val client_id="z3TxIQufXZltDjINoUn8EsBupe8h-I_mCIhxYTESgwU"

    override suspend fun savePhoto(image: Bitmap, photo:PhotoResponseItem?, listener: (Boolean) -> Unit) {
        val imageFileName = "JPEG_" + photo?.id + ".jpg"
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
       //     savedImagePath = imageFile.getAbsolutePath()
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

    override suspend fun getSearchPhotoResponse(query:String): Response<SearchPhotoResponse> {
        return UnsplashApi.UnsplashApi.getSerachPhoto(client_id,20,query)
    }

}