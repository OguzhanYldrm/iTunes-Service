package com.android.itunesservice.ui.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.itunesservice.R
import com.android.itunesservice.data.model.ProductRequestModel
import com.android.itunesservice.databinding.FragmentProductListBinding
import com.android.itunesservice.ui.adapters.ProductListAdapter
import com.android.itunesservice.ui.adapters.ProductLoadStateAdapter
import com.android.itunesservice.ui.viewmodel.ProductListViewModel
import com.google.android.material.tabs.TabLayout

class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private val viewModel by viewModels<ProductListViewModel> ()
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.bind(view)

        val adapter = ProductListAdapter()

        binding.apply {
            recyclerViewItems.layoutManager = GridLayoutManager(requireContext(),2)
            recyclerViewItems.setHasFixedSize(true)
            recyclerViewItems.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ProductLoadStateAdapter { adapter.retry() },
                footer = ProductLoadStateAdapter { adapter.retry() }
            )
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
                    1 -> category = "music"
                    2 -> category = "ebook"
                    3 -> category = "podcast"
                }
                var query = binding.SeachView.query.toString()
                if (query.isEmpty()){
                    query = "Michael+Jackson"
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
                    // #TODO Here, query should be converted to URL-Encoding like a+b+c

                    var category = "movie"
                    when(binding.categoryTab.selectedTabPosition){
                        0 -> category = "movie"
                        1 -> category = "music"
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

}