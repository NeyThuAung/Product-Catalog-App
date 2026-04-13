package com.neythuaung.product_catalog_app.product.domain.repository

import androidx.lifecycle.LiveData
import com.neythuaung.product_catalog_app.core.domain.Error
import com.neythuaung.product_catalog_app.core.domain.Result
import com.neythuaung.product_catalog_app.product.domain.entity.FavoriteProductEntity
import com.neythuaung.product_catalog_app.product.domain.entity.ProductList

interface ProductRepository {
    suspend fun getProductList(): Result<ProductList, Error>

    suspend fun upsert(product: FavoriteProductEntity) : Long

    fun getFavouriteProducts() : LiveData<List<FavoriteProductEntity>>

    suspend fun deleteProduct(product: FavoriteProductEntity)

}