package com.android.itunesservice.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.android.itunesservice.data.paging.ProductPagingSource

/**
 * This class has one api method if multiple ones come we will be adding those to this class.
 * It's methods are called from viewmodels and additionally we can create a caching mechanism here and
 * can retrieve cached results from database like room. However, now it's only connected to api.
 *
 */
class ProductRepository() {

    /**
     * After this method is called. It sets the max data size and pagesize for the query and starts
     * the call by invocation of a Pager object. And sets the Product PagingSource Class as pagingSourceFactory
     * that will handle the pagination process. And exposes it as liveData. Here we could also used
     * flow.
     */
    fun getSearchResults(query: String, entity: String) =
        Pager(
            config = PagingConfig(
            pageSize = 10,
            maxSize = 50,
            enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(query, entity) }
        ).liveData
}