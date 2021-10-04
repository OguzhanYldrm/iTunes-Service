package com.android.itunesservice.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.android.itunesservice.data.model.ProductRequestModel
import com.android.itunesservice.data.repository.ProductRepository


class ProductListViewModel() : ViewModel(){

    private val repository: ProductRepository = ProductRepository()

    private val currentRequest = MutableLiveData(ProductRequestModel(DEFUALT_QUERY,DEFAULT_ENTITY))

    val products = currentRequest.switchMap { request ->
        repository.getSearchResults(request.query, request.entity).cachedIn(viewModelScope)
    }

    fun searchProducts(newRequest: ProductRequestModel) {
        currentRequest.value = newRequest
    }

    companion object {
        const val DEFUALT_QUERY = "APPLE"
        const val DEFAULT_ENTITY = "movie"
    }
}