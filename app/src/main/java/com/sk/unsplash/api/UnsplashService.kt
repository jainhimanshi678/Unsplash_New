package com.sk.unsplash.api

import com.sk.unsplash.constants.StringConstants
import com.sk.unsplash.models.photo.PhotoResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UnsplashService {

    @GET("photos")
    suspend fun getPhotos(
        @Query("client_id") query: String,
    ): Response<PhotoResponse>
}