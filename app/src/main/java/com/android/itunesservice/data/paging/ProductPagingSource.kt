package com.android.itunesservice.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.itunesservice.api.ISearchService
import com.android.itunesservice.data.model.ProductResultModel
import com.android.itunesservice.utils.PRODUCT_PAGE_NUMBER
import retrofit2.HttpException
import java.io.IOException


/**
 *This class handles the pagination of returned products. It takes the query and entity as param and
 * make retrofit call. After result returned it divides result into pages with previous and next page
 * positions.
 */

class ProductPagingSource(
    private val searchServiceApi: ISearchService,
    private val query: String,
    private val entity: String
) : PagingSource<Int, ProductResultModel.Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductResultModel.Product> {
        val position = params.key ?: PRODUCT_PAGE_NUMBER

        return try {
            //Retrofit call for query
            val response = searchServiceApi.getSearchedProducts(query,  "US", entity, params.loadSize, position)
            //Getting products from the ProductResultModels Product section
            val products = response.results

            //Loading the returned result to pages.
            LoadResult.Page(
                data = products,
                prevKey = if (position == PRODUCT_PAGE_NUMBER) null else position - 1,
                nextKey = if (products.isEmpty()) null else position + 1
                )
        } catch (e : IOException){
            LoadResult.Error(e)
        } catch (e : HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductResultModel.Product>): Int? = null

}