package com.android.itunesservice.ui.pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.android.itunesservice.R
import com.android.itunesservice.data.model.ProductRequestModel
import com.android.itunesservice.data.model.ProductResultModel
import com.android.itunesservice.databinding.FragmentProductListBinding
import com.android.itunesservice.enums.Category
import com.android.itunesservice.ui.MainActivity
import com.android.itunesservice.ui.adapters.ProductListAdapter
import com.android.itunesservice.ui.adapters.ProductLoadStateAdapter
import com.android.itunesservice.ui.viewmodel.ProductListViewModel
import com.android.itunesservice.utils.DEFAULT_CATEGORY
import com.android.itunesservice.utils.DEFAULT_QUERY
import com.android.itunesservice.utils.UtilFunctions.urlEncoder
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : Fragment(R.layout.fragment_product_list), ProductListAdapter.OnProductClickListener {

    private val viewModel by viewModels<ProductListViewModel> ()
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private var searchJob: Job? = null

    private val adapter = ProductListAdapter(this)

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.bind(view)

        //Setting recyclerview with reload options and gridlayout structure
        initAdapter()

        //Initial call for searchRequest
        (activity as MainActivity).apply {
            doSearch(this.QUERY_STATE, this.CATEGORY_STATE) }

        //Initiating search actions
        initSearchActions()

        //Initiating tab selections
        initTabOperations()

    }

    private fun initAdapter() {
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

        //Setting LoadStateListener to handle page specific loading condition.(different from ProductLoadStateAdapter
        //which was for recyclerview specific loading states (like couldn't load a page))
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBarProductList.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewItems.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnReloadProducts.isVisible = loadState.source.refresh is LoadState.Error
                resultsNotLoadedProductList.isVisible = loadState.source.refresh is LoadState.Error

                Log.d("Load State",loadState.source.refresh.toString())
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
    }

    @ExperimentalPagingApi
    private fun initSearchActions() {
        //Setting query change event
        binding.SeachView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    binding.recyclerViewItems.scrollToPosition(0)

                    val encodedQuery = query.urlEncoder()
                    val category = getEntity(binding.categoryTab.selectedTabPosition)

                    doSearch(encodedQuery, category)

                    binding.SeachView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(p0: String?) = true

        })
    }

    @ExperimentalPagingApi
    private fun initTabOperations() {
        //Adding Category tabs
        binding.categoryTab.apply {
            addTab(binding.categoryTab.newTab().setText(context.getString(R.string.movies)))
            addTab(binding.categoryTab.newTab().setText(context.getString(R.string.musics)))
            addTab(binding.categoryTab.newTab().setText(context.getString(R.string.books)))
            addTab(binding.categoryTab.newTab().setText(context.getString(R.string.podcasts)))
            tabGravity = TabLayout.GRAVITY_FILL
        }

        //Setting category selection events.
        binding.categoryTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //getting the category string by checking its position
                val category = getEntity(tab.position)
                //getting query as URL-Encoded
                var query = binding.SeachView.query.toString().urlEncoder()
                //Setting default value for query if it's empty.
                if (query.isEmpty()){
                    query = DEFAULT_QUERY
                }

                doSearch(query, category)

                //Closing keyboard
                binding.SeachView.clearFocus()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }



    /**
     * This method gets the necessary params to search and starts a coroutine job
     * to handle the operation and submit returned PageData into adapter
     */
    @ExperimentalPagingApi
    private fun doSearch(query: String, category: String) {
        //on each search we save the state to the parent(MainActivity)
        setQueryState(query, category)

        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchProducts(ProductRequestModel(query, category))
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }




    /**
     * @param position the position of selected tab
     * @return query string for category.
     */
    private fun getEntity(position : Int) : String{
        when(position){
            0 -> return Category.MOVIE.name.lowercase()
            1 -> return Category.SONG.name.lowercase()
            2 -> return Category.EBOOK.name.lowercase()
            3 -> return Category.PODCAST.name.lowercase()
        }
        return Category.MOVIE.name.lowercase()
    }

    override fun onProductClick(product: ProductResultModel.Product) {
        val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(product)
        findNavController().navigate(action)
    }

    private fun setQueryState(query: String, category: String) {
        (activity as MainActivity?)?.QUERY_STATE = query
        (activity as MainActivity?)?.CATEGORY_STATE = category
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("onDestroy", "called")
        _binding = null
    }
}