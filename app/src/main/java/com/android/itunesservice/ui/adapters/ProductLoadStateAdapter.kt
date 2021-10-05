package com.android.itunesservice.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.itunesservice.databinding.ProductLoadStateBinding

/**
 *This adapter class is a state class that checks the loading state of recyclerview items.
 * It shows errors and a reload button on sceanarios like no internet connection, request timeout, http
 * errors. Takes a relod function as parameter to trigger the reload operation when it's needed.
 */
class ProductLoadStateAdapter(private val reload: () -> Unit) : LoadStateAdapter<ProductLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            ProductLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    inner class LoadStateViewHolder(private val binding: ProductLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnProductReload.setOnClickListener {
                reload.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                pbProductLoading.isVisible = loadState is LoadState.Loading
                btnProductReload.isVisible = loadState !is LoadState.Loading
                tvProductError.isVisible = loadState !is LoadState.Loading

            }
        }

    }

}