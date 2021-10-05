package com.android.itunesservice.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.itunesservice.api.ISearchService
import com.android.itunesservice.data.model.ProductResultModel
import com.android.itunesservice.data.paging.ProductPagingSource
import com.android.itunesservice.utils.DEFAULT_CATEGORY
import com.android.itunesservice.utils.DEFAULT_QUERY
import com.android.itunesservice.utils.MAX_SIZE
import com.android.itunesservice.utils.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class has one api method if multiple ones come we will be adding those to this class.
 * It's methods are called from viewmodels and additionally we can create a caching mechanism here and
 * can retrieve cached results from database like room. However, now it's only connected to api.
 */
@Singleton
class ProductRepository @Inject constructor(private val searchServiceApi: ISearchService ) {

    /**
     * After this method is called. It sets the max data size and pagesize for the query and starts
     * the call by invocation of a Pager object. And sets the Product PagingSource Class as pagingSourceFactory
     * that will handle the pagination process. And exposes it as liveData. Here we could also used
     * flow.(On latest update it's changed to Flow)
     */
    fun getSearchResults(query: String, entity: String) : Flow<PagingData<ProductResultModel.Product>>
    {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(searchServiceApi, query, entity) }
        ).flow
    }
}