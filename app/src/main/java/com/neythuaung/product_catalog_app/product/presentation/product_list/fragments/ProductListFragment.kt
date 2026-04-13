package com.neythuaung.product_catalog_app.product.presentation.product_list.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.neythuaung.product_catalog_app.R
import com.neythuaung.product_catalog_app.core.utils.showToast
import com.neythuaung.product_catalog_app.databinding.FragmentProductListBinding
import com.neythuaung.product_catalog_app.product.domain.entity.FavoriteProductEntity
import com.neythuaung.product_catalog_app.product.presentation.product_list.fragments.adapter.ProductListAdapter
import com.neythuaung.product_catalog_app.product.presentation.view_model.ProductViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.getValue

class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var productListAdapter: ProductListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.llProductNotFoun.isVisible = false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        if (viewModel.productList.isEmpty()) {
            viewModel.getProductList()
        }
        binding.btnRetry.setOnClickListener {
            viewModel.getProductList()
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getProductList()
        }

        viewModel.favoriteProducts.observe(viewLifecycleOwner) { favorites ->
            productListAdapter.setFavorites(favorites)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.productListState.collectLatest { state ->

                when {

                    state.isLoading -> {
                        binding.llLoading.isVisible = !binding.swipeRefresh.isRefreshing
                        binding.errorLayout.isVisible = false
                    }

                    state.error != null -> {
                        binding.swipeRefresh.isRefreshing = false
                        binding.llLoading.isVisible = false

                        binding.errorLayout.isVisible = true
                        binding.swipeRefresh.isVisible = false

                        binding.tvError.text = state.error
                    }

                    else -> {
                        binding.swipeRefresh.isRefreshing = false
                        binding.llLoading.isVisible = false

                        binding.errorLayout.isVisible = false
                        binding.swipeRefresh.isVisible = true

                        productListAdapter.updateData(state.success)
                    }
                }
            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                productListAdapter.filter.filter(s.toString())
            }
        })

        productListAdapter.setOnEmptyStateListener { isEmpty ->
            binding.apply {
                llProductNotFoun.isVisible = isEmpty
                swipeRefresh.isVisible = !isEmpty
            }
        }

    }

    private fun initRecyclerView() {
        productListAdapter = ProductListAdapter(
            viewModel.productList,
            onProductClick = { product ->
                viewModel.productDetail = product
                findNavController().navigate(R.id.action_productFragment_to_productDetailsFragment)
            },
            onFavouriteProductClick = { product, isFavorite ->
                val entity = FavoriteProductEntity(
                    id = product.id!!.toInt(),
                    title = product.title!!,
                    brand = product.brand,
                    price = product.price!!,
                    thumbnail = product.thumbnail!!
                )
                viewModel.toggleFavorite(entity, isFavorite)
            }
        )
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager =
                GridLayoutManager(
                    requireContext(), 2,
                    LinearLayoutManager.VERTICAL, false
                )
            adapter = productListAdapter
        }
    }
}