package com.neythuaung.product_catalog_app.product.presentation.favourite_products.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.neythuaung.product_catalog_app.R
import com.neythuaung.product_catalog_app.core.utils.showToast
import com.neythuaung.product_catalog_app.databinding.FragmentFavouriteBinding
import com.neythuaung.product_catalog_app.product.domain.entity.FavoriteProductEntity
import com.neythuaung.product_catalog_app.product.presentation.favourite_products.fragments.adapter.FavouriteProductListAdapter
import com.neythuaung.product_catalog_app.product.presentation.product_list.fragments.adapter.ProductListAdapter
import com.neythuaung.product_catalog_app.product.presentation.view_model.ProductViewModel
import kotlin.getValue

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var favouriteProductListAdapter: FavouriteProductListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        viewModel.favoriteProducts.observe(viewLifecycleOwner) { favorites ->
            binding.apply {
                if (favorites.isNullOrEmpty()) {
                    recyclerView.isVisible = false
                    llProductNotFoun.isVisible = true
                } else {
                    recyclerView.isVisible = true
                    llProductNotFoun.isVisible = false
                }
            }
            favouriteProductListAdapter.updateData(favorites)

        }
    }

    private fun initRecyclerView() {
        favouriteProductListAdapter = FavouriteProductListAdapter(
            arrayListOf(),
            onFavouriteProductClick = { product ->
                viewModel.toggleFavorite(product, true)
            }
        )
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager =
                GridLayoutManager(
                    requireContext(), 2,
                    LinearLayoutManager.VERTICAL, false
                )
            adapter = favouriteProductListAdapter
        }
    }
}