package com.android.itunesservice.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.android.itunesservice.data.paging.ProductPagingSource

class ProductRepository() {

    fun getSearchResults(query: String, entity: String) =
        Pager(
            config = PagingConfig(
            pageSize = 20,
            maxSize = 200,
            enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(query, entity) }
        ).liveData
}