package com.android.itunesservice.ui.pages

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.itunesservice.R
import com.android.itunesservice.data.model.ProductResultModel
import com.android.itunesservice.databinding.FragmentProductDetailBinding
import com.android.itunesservice.utils.UtilFunctions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val args by navArgs<ProductDetailFragmentArgs>()
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailBinding.bind(view)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        //Getting Parcelable Product object from args of navigation component.
        binding.apply {
            val product = args.product as ProductResultModel.Product

            tvProductDetailDescription.text = product.longDescription
            tvProductDetailName.text = product.collectionName
            tvProductDetailPrice.text = "$ ${product.collectionPrice}"
            tvProductDetailReleaseDate.text = UtilFunctions.dateFormatter(product.releaseDate)
            tvProductDetailGenre.text = product.primaryGenreName

            Glide.with(this@ProductDetailFragment)
                .load(product.artworkUrl100)
                .error(R.drawable.product_placeholder)
                //Listening image load and handling load and error conditions
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        productDetailProgressBar.isVisible = false
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        productDetailProgressBar.isVisible = false
                        tvProductDetailDescription.isVisible = true
                        tvProductDetailName.isVisible = true
                        tvProductDetailPrice.isVisible = true
                        tvProductDetailReleaseDate.isVisible = true
                        tvProductDetailGenre.isVisible = true
                        return false
                    }

                })
                .into(ivProductDetailImage)




        }

        //Back button action for returning to list page
        binding.btnProductDetailBack.setOnClickListener {
            val action = ProductDetailFragmentDirections.actionProductDetailFragmentToProductListFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}