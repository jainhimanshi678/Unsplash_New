package com.sk.fabsplash.repository

import android.util.Log
import com.sk.fabsplash.api.UnsplashApi
import com.sk.fabsplash.models.collection.CollectionResult
import com.sk.fabsplash.models.photo.PhotoResponse
import com.sk.fabsplash.models.searchCollection.SearchCollectionResponse
import com.sk.fabsplash.models.searchPhoto.SearchPhotoResponse
import retrofit2.Response

object RemoteDataRepository : IRemoteDataRepository {

    private const val client_id = "z3TxIQufXZltDjINoUn8EsBupe8h-I_mCIhxYTESgwU"

    override suspend fun getPhoto(count: Int): Response<PhotoResponse> {
        return UnsplashApi.UnsplashApi.getPhotos(client_id, 200, count)
    }

    override suspend fun getSearchPhotoResponse(
        query: String,
        count: Int
    ): Response<SearchPhotoResponse> {
        return UnsplashApi.UnsplashApi.getSerachPhoto(client_id, 20, query, count)
    }

    override suspend fun getCollection(count: Int): Response<CollectionResult> {
        return UnsplashApi.UnsplashApi.getCollections(client_id, 200, count)
    }

    override suspend fun getSearchCollection(
        query: String,
        count: Int
    ): Response<SearchCollectionResponse>? {
        var result: Response<SearchCollectionResponse>? = null
        result= UnsplashApi.UnsplashApi.searchCollection(client_id, 200, query)
        try {

        }catch (e:Exception){
            Log.d("error", "getSearchCollection: $e")
        }
        return result
    }

}