package com.android.itunesservice.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.itunesservice.data.model.ProductResultModel
import com.android.itunesservice.databinding.ItemProductBinding
import com.android.itunesservice.utils.loadImage
import com.android.itunesservice.utils.notFoundPlaceholder

class ProductListAdapter :
    PagingDataAdapter<ProductResultModel.Product, ProductListAdapter.ProductListViewHolder>(PRODUCT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val currentProduct = getItem(position)
        currentProduct?.let {
            holder.bind(currentProduct)
        }
    }

    class ProductListViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductResultModel.Product) {
            binding.apply {
                productImage.loadImage(
                    product.artworkUrl100,
                    notFoundPlaceholder(binding.root.context)
                )
                productName.text = product.collectionName
                productPrice.text = "$ ${product.collectionPrice}"
            }
        }
    }

    companion object {
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<ProductResultModel.Product>() {

            override fun areItemsTheSame(oldItem: ProductResultModel.Product, newItem: ProductResultModel.Product) =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ProductResultModel.Product, newItem: ProductResultModel.Product) =
                oldItem == newItem
        }
    }
}