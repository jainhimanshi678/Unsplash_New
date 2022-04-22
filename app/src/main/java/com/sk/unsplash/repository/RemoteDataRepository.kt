package com.sk.unsplash.repository

import com.sk.unsplash.api.UnsplashApi
import com.sk.unsplash.models.photo.PhotoResponse
import com.sk.unsplash.models.searchPhoto.SearchPhotoResponse
import retrofit2.Response

object RemoteDataRepository:IRemoteDataRepository {

    private const val client_id="z3TxIQufXZltDjINoUn8EsBupe8h-I_mCIhxYTESgwU"

    override suspend fun getPhoto(): Response<PhotoResponse> {
        return UnsplashApi.UnsplashApi.getPhotos(client_id,200)
    }

    override suspend fun getSearchPhotoResponse(query:String): Response<SearchPhotoResponse> {
        return UnsplashApi.UnsplashApi.getSerachPhoto(client_id,20,query)
    }

}