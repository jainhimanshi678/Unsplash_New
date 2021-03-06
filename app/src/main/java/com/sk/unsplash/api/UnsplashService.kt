package com.sk.unsplash.api

import com.sk.unsplash.constants.StringConstants
import com.sk.unsplash.models.photo.PhotoResponse
import com.sk.unsplash.models.searchPhoto.SearchPhotoResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UnsplashService {

    @GET("photos")
    suspend fun getPhotos(
        @Query("client_id") query: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): Response<PhotoResponse>

    @GET("search/photos")
    suspend fun getSerachPhoto(
        @Query("client_id") query: String,
        @Query("per_page") per_page: Int,
        @Query("query") item:String,
        @Query("page") page: Int
    ): Response<SearchPhotoResponse>

    @GET("users/:username/photos")
    suspend fun getUserCollection(
        @Query("client_id") query: String,
        @Query("per_page") per_page: Int,
        @Query("username") username:String,
    ): Response<SearchPhotoResponse>
}