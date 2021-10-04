package com.android.itunesservice.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.itunesservice.api.SearchServiceApi
import com.android.itunesservice.data.model.ProductResultModel
import retrofit2.HttpException
import java.io.IOException

private const val PRODUCT_PAGE_NUMBER = 0

class ProductPagingSource(
    private val query: String,
    private val entity: String
) : PagingSource<Int, ProductResultModel.Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductResultModel.Product> {
        val position = params.key ?: PRODUCT_PAGE_NUMBER

        return try {

            val response = SearchServiceApi.getSearchedProducts(query, entity, params.loadSize, position)
            val products = response.results

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