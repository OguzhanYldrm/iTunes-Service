package com.android.itunesservice.ui.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.itunesservice.R
import com.android.itunesservice.data.model.ProductRequestModel
import com.android.itunesservice.databinding.FragmentProductListBinding
import com.android.itunesservice.ui.adapters.ProductListAdapter
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
            recyclerViewItems.setHasFixedSize(true)
            recyclerViewItems.adapter = adapter
            recyclerViewItems.layoutManager = GridLayoutManager(requireContext(),2)
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
                when(tab.text) {
                    "Movies" -> viewModel.searchProducts(ProductRequestModel("apple", "movie"))
                    "Musics" -> Toast.makeText(context, "Musics", Toast.LENGTH_SHORT).show()
                    "Books" -> Toast.makeText(context, "Books", Toast.LENGTH_SHORT).show()
                    "Podcasts" -> Toast.makeText(context, "Podcasts", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}