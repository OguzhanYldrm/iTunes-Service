package com.android.itunesservice.api

import com.android.itunesservice.data.model.ProductModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class SearchServiceApi {

    val BASE_URL = "https://itunes.apple.com/"
    val country = Locale.getDefault().country

    val API = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ISearchService::class.java)

    suspend fun getSearchedProducts(
        searchText: String = "",
        entity: String = "movie",
        pageSize: Int = 20,
        pageNumber: Int = 0
    ): Call<List<ProductModel>> {
        return API.getSearchedProducts(searchText, country, entity, pageSize, pageNumber)
    }

}