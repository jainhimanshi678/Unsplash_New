package com.sk.unsplash.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sk.unsplash.constants.NumericalConstant
import com.sk.unsplash.constants.StringConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object UnsplashApi {
    /**
     * Creates a retrofit builder object for unsplash api.
     */
    private val unsplashRetrofit by lazy {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.readTimeout(NumericalConstant.TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(NumericalConstant.TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .baseUrl(StringConstants.UNSPLASH_API_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * The photo api variable to access all unsplash remote api methods.
     */
    val UnsplashApi: UnsplashService by lazy {
        unsplashRetrofit.create(UnsplashService::class.java)
    }
}