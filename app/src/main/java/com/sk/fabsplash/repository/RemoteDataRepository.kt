package com.sk.fabsplash.repository

import com.sk.fabsplash.api.UnsplashApi
import com.sk.fabsplash.models.photo.PhotoResponse
import com.sk.fabsplash.models.searchPhoto.SearchPhotoResponse
import retrofit2.Response

object RemoteDataRepository : IRemoteDataRepository {

    private const val client_id = "z3TxIQufXZltDjINoUn8EsBupe8h-I_mCIhxYTESgwU"

    override suspend fun getPhoto(count: Int): Response<PhotoResponse> {
        return UnsplashApi.UnsplashApi.getPhotos(client_id, 200, count)
    }

    override suspend fun getSearchPhotoResponse(query: String, count: Int): Response<SearchPhotoResponse> {
        return UnsplashApi.UnsplashApi.getSerachPhoto(client_id, 20, query,count)
    }

}