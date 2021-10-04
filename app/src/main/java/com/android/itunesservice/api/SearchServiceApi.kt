package com.android.itunesservice.api

import com.android.itunesservice.data.model.ProductResultModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


object SearchServiceApi {

    private val BASE_URL = "https://itunes.apple.com/"
    private val country = Locale.getDefault().country

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .build()

    val API = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
         //.client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ISearchService::class.java)

    suspend fun getSearchedProducts(
        searchText: String,
        entity: String,
        pageSize: Int,
        pageNumber: Int
    ): ProductResultModel {
        return API.getSearchedProducts(searchText, country, entity, pageSize, pageNumber)
    }

}