package com.sk.fabsplash.api

import com.sk.fabsplash.models.photo.PhotoResponse
import com.sk.fabsplash.models.searchPhoto.SearchPhotoResponse
import retrofit2.Response
import retrofit2.http.GET
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