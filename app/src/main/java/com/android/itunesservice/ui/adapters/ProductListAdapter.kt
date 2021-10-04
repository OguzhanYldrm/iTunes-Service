package com.android.itunesservice.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.itunesservice.R
import com.android.itunesservice.data.model.ProductResultModel
import com.android.itunesservice.databinding.ItemProductBinding
import com.android.itunesservice.utils.UtilFunctions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ProductListAdapter(private val listener: OnProductClickListener) :
    PagingDataAdapter<ProductResultModel.Product, ProductListAdapter.ProductListViewHolder>(
        PRODUCT_COMPARATOR
    ) {

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

    inner class ProductListViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    item?.let {
                        listener.onProductClick(item)
                    }
                }
            }
        }

        fun bind(product: ProductResultModel.Product) {
            binding.apply {
                Glide.with(itemView)
                    .load(product.artworkUrl100)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.product_placeholder)
                    .into(productImage)

                productName.text = UtilFunctions.textShortener(product.collectionName, 30)
                productPrice.text = "$ ${product.collectionPrice}"
                productReleaseDate.text = UtilFunctions.dateFormatter(product.releaseDate)
            }
        }
    }

    interface OnProductClickListener {
        fun onProductClick(product: ProductResultModel.Product)
    }

    companion object {
        private val PRODUCT_COMPARATOR =
            object : DiffUtil.ItemCallback<ProductResultModel.Product>() {

                override fun areItemsTheSame(
                    oldItem: ProductResultModel.Product,
                    newItem: ProductResultModel.Product
                ) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: ProductResultModel.Product,
                    newItem: ProductResultModel.Product
                ): Boolean {
                    return oldItem == newItem
                }


            }
    }
}