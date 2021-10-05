package com.android.itunesservice.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.android.itunesservice.data.model.ProductRequestModel
import com.android.itunesservice.data.repository.ProductRepository


class ProductListViewModel() : ViewModel(){

    //Initiating repository object. These parts can be done via Hilt Dependency Injection
    private val repository: ProductRepository = ProductRepository()

    //Request object as livedata
    private val currentRequest = MutableLiveData(ProductRequestModel(DEFUALT_QUERY,DEFAULT_ENTITY))

    //This method is triggered if currentRequest live data object is changed.
    val products = currentRequest.switchMap { request ->
        repository.getSearchResults(request.query, request.entity).cachedIn(viewModelScope)
    }

    //Method for making a new api call for search or tab selection.
    fun searchProducts(newRequest: ProductRequestModel) {
        currentRequest.value = newRequest
    }

    companion object {
        const val DEFUALT_QUERY = "Hobbit"
        const val DEFAULT_ENTITY = "movie"
    }
}