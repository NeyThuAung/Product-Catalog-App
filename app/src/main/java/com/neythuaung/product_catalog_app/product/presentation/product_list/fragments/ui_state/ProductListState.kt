package com.neythuaung.product_catalog_app.product.presentation.product_list.fragments.ui_state

import com.neythuaung.product_catalog_app.product.domain.entity.Product

data class ProductListState(
    val isLoading: Boolean = false,
    val success: List<Product> = emptyList(),
    val error: String? = null,
)
