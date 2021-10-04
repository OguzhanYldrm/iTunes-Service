package com.android.itunesservice.ui.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.itunesservice.R
import com.android.itunesservice.data.model.ProductRequestModel
import com.android.itunesservice.data.model.ProductResultModel
import com.android.itunesservice.databinding.FragmentProductListBinding
import com.android.itunesservice.ui.adapters.ProductListAdapter
import com.android.itunesservice.ui.adapters.ProductLoadStateAdapter
import com.android.itunesservice.ui.viewmodel.ProductListViewModel
import com.google.android.material.tabs.TabLayout

class ProductListFragment : Fragment(R.layout.fragment_product_list), ProductListAdapter.OnProductClickListener {

    private val viewModel by viewModels<ProductListViewModel> ()
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.bind(view)

        val adapter = ProductListAdapter(this)

        binding.apply {
            recyclerViewItems.setHasFixedSize(true)
            recyclerViewItems.itemAnimator = null
            recyclerViewItems.layoutManager = GridLayoutManager(requireContext(),2)
            recyclerViewItems.setHasFixedSize(true)
            recyclerViewItems.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ProductLoadStateAdapter { adapter.retry() },
                footer = ProductLoadStateAdapter { adapter.retry() }
            )
            btnReloadProducts.setOnClickListener {
                adapter.retry()
            }
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBarProductList.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewItems.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnReloadProducts.isVisible = loadState.source.refresh is LoadState.Error
                resultsNotLoadedProductList.isVisible = loadState.source.refresh is LoadState.Error

                //If no products retrieved
                if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1){
                    recyclerViewItems.isVisible = false
                    resultsNotFoundProductList.isVisible = true
                } else {
                    resultsNotFoundProductList.isVisible = false
                }
            }
        }

        //Get Products
        viewModel.products.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.categoryTab.apply {
            addTab(binding.categoryTab.newTab().setText("Movies"))
            addTab(binding.categoryTab.newTab().setText("Musics"))
            addTab(binding.categoryTab.newTab().setText("Books"))
            addTab(binding.categoryTab.newTab().setText("Podcasts"))
            tabGravity = TabLayout.GRAVITY_FILL
        }


        binding.categoryTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                var category = "movie"
                when(tab.position) {
                    0 -> category = "movie"
                    1 -> category = "song"
                    2 -> category = "ebook"
                    3 -> category = "podcast"
                }
                var query = binding.SeachView.query.toString()
                if (query.isEmpty()){
                    query = "Hobbit"
                }
                viewModel.searchProducts(ProductRequestModel(query, category))
                binding.SeachView.clearFocus()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.SeachView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    binding.recyclerViewItems.scrollToPosition(0)
                    // TODO Here, query should be converted to URL-Encoding like a+b+c

                    var category = "movie"
                    when(binding.categoryTab.selectedTabPosition){
                        0 -> category = "movie"
                        1 -> category = "song"
                        2 -> category = "ebook"
                        3 -> category = "podcast"
                    }
                    viewModel.searchProducts(ProductRequestModel(query, category))
                    binding.SeachView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onProductClick(product: ProductResultModel.Product) {
        val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(product)
        findNavController().navigate(action)
    }

}