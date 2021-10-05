package com.android.itunesservice.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.itunesservice.data.model.ProductRequestModel
import com.android.itunesservice.data.model.ProductResultModel
import com.android.itunesservice.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Repository is injected by hilt automatically
@HiltViewModel
class ProductListViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel(){


    private var currentResult : Flow<PagingData<ProductResultModel.Product>>? = null

    @ExperimentalPagingApi
    fun searchProducts(request: ProductRequestModel): Flow<PagingData<ProductResultModel.Product>> {
        val newResult = repository.getSearchResults(request.query, request.entity).cachedIn(viewModelScope)
        //updating the paging data
        currentResult = newResult
        return newResult
    }

}