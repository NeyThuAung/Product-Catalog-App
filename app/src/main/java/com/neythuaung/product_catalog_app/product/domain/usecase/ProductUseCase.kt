package com.neythuaung.product_catalog_app.product.domain.usecase

import androidx.lifecycle.LiveData
import com.neythuaung.product_catalog_app.core.domain.Error
import com.neythuaung.product_catalog_app.core.domain.Result
import com.neythuaung.product_catalog_app.product.domain.entity.FavoriteProductEntity
import com.neythuaung.product_catalog_app.product.domain.entity.ProductList
import com.neythuaung.product_catalog_app.product.domain.repository.ProductRepository
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Result<ProductList, Error> {
        return productRepository.getProductList()
    }

    suspend fun upsert(product: FavoriteProductEntity): Long =
        productRepository.upsert(product)

    fun getFavouriteProducts() : LiveData<List<FavoriteProductEntity>> =
        productRepository.getFavouriteProducts()


    suspend fun deleteProduct(product: FavoriteProductEntity) =
        productRepository.deleteProduct(product)


}