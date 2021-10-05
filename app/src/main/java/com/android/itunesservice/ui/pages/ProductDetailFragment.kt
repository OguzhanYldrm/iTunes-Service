package com.android.itunesservice.ui.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.imageLoader
import coil.request.ImageRequest
import com.android.itunesservice.R
import com.android.itunesservice.data.model.ProductResultModel
import com.android.itunesservice.databinding.FragmentProductDetailBinding
import com.android.itunesservice.utils.UtilFunctions.dateFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            val price = "$ ${product.collectionPrice}"

            tvProductDetailDescription.text = product.longDescription
            tvProductDetailName.text = product.collectionName
            tvProductDetailPrice.text = price
            tvProductDetailReleaseDate.text = product.releaseDate?.dateFormatter()
            tvProductDetailGenre.text = product.primaryGenreName

            //Loading images via Coil Lib
            val request = context?.let {
                ImageRequest.Builder(it)
                    .placeholder(R.drawable.product_placeholder)
                    .error(R.drawable.product_not_found)
                    .data(product.artworkUrl100)
                    .target(
                        //Displaying placeholder initially
                        onStart = { placeholder ->
                            ivProductDetailImage.background = placeholder
                        },
                        //Success case open visibilities of items
                        onSuccess = { result ->
                            productDetailProgressBar.isVisible = false
                            tvProductDetailDescription.isVisible = true
                            tvProductDetailName.isVisible = true
                            tvProductDetailPrice.isVisible = true
                            tvProductDetailReleaseDate.isVisible = true
                            tvProductDetailGenre.isVisible = true
                            ivProductDetailImage.background = result
                        },
                        //Error case show error background
                        onError = { error ->
                            productDetailProgressBar.isVisible = false
                            ivProductDetailImage.background = error
                        }
                    )
                    .build()
            }
            if (request != null) {
                context?.imageLoader?.enqueue(request)
            }




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