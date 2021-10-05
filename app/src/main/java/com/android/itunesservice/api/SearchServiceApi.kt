package com.android.itunesservice.api

import com.android.itunesservice.data.model.ProductResultModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


object SearchServiceApi {

    //Initiating base_url and getting country code from system.
    private val BASE_URL = "https://itunes.apple.com/"
    private val country = Locale.getDefault().country

    // This is a utility interceptor which logs every request with detail to the logcat.
    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //Okhttp object of the interceptor to add as client to retrofit object.
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .build()

    val API = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        //.client(okHttpClient) //logs will be visible on logcat when this comment is opened.
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ISearchService::class.java)

    //the transition method for the ISearchService's getSearchedProducts method.
    suspend fun getSearchedProducts(
        searchText: String,
        entity: String,
        pageSize: Int,
        pageNumber: Int
    ): ProductResultModel {
        return API.getSearchedProducts(searchText, country, entity, pageSize, pageNumber)
    }

}