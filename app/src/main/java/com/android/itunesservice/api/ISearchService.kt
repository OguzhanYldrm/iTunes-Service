package com.android.itunesservice.api

import com.android.itunesservice.data.model.ProductRequestModel
import com.android.itunesservice.data.model.ProductResultModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ISearchService {

    @GET("search")
    suspend fun getSearchedProducts(
        @Query("term") searchText: String,
        @Query("country") country: String,
        @Query("entity") entity: String,
        @Query("limit") pageSize: Int,
        @Query("offset") pageNumber: Int
    ): ProductResultModel

}