package com.neythuaung.product_catalog_app.product.presentation.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neythuaung.product_catalog_app.core.domain.onError
import com.neythuaung.product_catalog_app.core.domain.onSuccess
import com.neythuaung.product_catalog_app.core.presentation.toUiErrorType
import com.neythuaung.product_catalog_app.core.presentation.toUserMessage
import com.neythuaung.product_catalog_app.core.utils.showToast
import com.neythuaung.product_catalog_app.product.domain.entity.FavoriteProductEntity
import com.neythuaung.product_catalog_app.product.domain.entity.Product
import com.neythuaung.product_catalog_app.product.domain.usecase.ProductUseCase
import com.neythuaung.product_catalog_app.product.presentation.product_list.fragments.ui_state.ProductListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val application: Application
) : ViewModel() {

    var productList: ArrayList<Product> = arrayListOf()
    var productDetail : Product = Product()
    private val _productListState = MutableStateFlow(ProductListState())
    val productListState = _productListState
    fun getProductList() {
        _productListState.update {
            it.copy(
                isLoading = true,
                error = null,
                success = emptyList()
            )
        }
        viewModelScope.launch {
            productUseCase()
                .onSuccess { data ->
                    _productListState.update {
                        it.copy(
                            isLoading = false,
                            success = data.products
                        )
                    }
                }
                .onError { dataError ->
                    val message = dataError
                        .toUiErrorType()
                        .toUserMessage(dataError)

                    _productListState.update {
                        it.copy(
                            isLoading = false,
                            error = message
                        )
                    }
                }
        }

    }

    val favoriteProducts = productUseCase.getFavouriteProducts()

    fun toggleFavorite(product: FavoriteProductEntity, isFavorite: Boolean) {
        viewModelScope.launch {

            if (isFavorite) {
                productUseCase.deleteProduct(product)
                application.showToast("Removed from favourite.")
            } else {
                application.showToast("Successfully saved to favourite.")
                productUseCase.upsert(product)
            }
        }
    }

}