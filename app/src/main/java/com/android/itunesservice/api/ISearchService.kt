package com.android.itunesservice.api

import com.android.itunesservice.data.model.ProductRequestModel
import com.android.itunesservice.data.model.ProductResultModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ISearchService {

    /**
     * Method that retrieves searched results and returns as a ProductResultModel object.
     * @param searchText the query that user enters to searchbox
     * @param country the local country code of user, is taken automatically from system
     * @param entity the category  that user searches. (movie,music,ebook,podcast)
     * @param pageSize the page size of one request
     * @param pageNumber page number of the requesting data
     * @return ProductResultModel
     */
    @GET("search")
    suspend fun getSearchedProducts(
        @Query("term") searchText: String,
        @Query("country") country: String,
        @Query("entity") entity: String,
        @Query("limit") pageSize: Int,
        @Query("offset") pageNumber: Int
    ): ProductResultModel

}