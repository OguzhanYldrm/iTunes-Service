package com.android.itunesservice.ui.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.android.itunesservice.databinding.FragmentProductListBinding
import com.android.itunesservice.ui.viewmodel.ProductListViewModel
import com.google.android.material.tabs.TabLayout

class ProductListFragment : Fragment() {

    private val viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
    private lateinit var binding: FragmentProductListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductListBinding.inflate(inflater, container, false)

        //Get Products
        retrieveProducts()

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
                    "Movies" -> Toast.makeText(context, "Movies", Toast.LENGTH_SHORT).show()
                    "Musics" -> Toast.makeText(context, "Musics", Toast.LENGTH_SHORT).show()
                    "Books" -> Toast.makeText(context, "Books", Toast.LENGTH_SHORT).show()
                    "Podcasts" -> Toast.makeText(context, "Podcasts", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        return binding.root
    }

    private fun retrieveProducts() {
        viewModel.products.observe(viewLifecycleOwner) {

        }
    }

}