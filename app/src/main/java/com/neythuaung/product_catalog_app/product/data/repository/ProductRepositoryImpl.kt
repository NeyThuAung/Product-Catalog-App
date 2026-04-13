package com.neythuaung.product_catalog_app.product.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.neythuaung.product_catalog_app.core.data.SafeApiCall
import com.neythuaung.product_catalog_app.core.domain.Error
import com.neythuaung.product_catalog_app.core.domain.Result
import com.neythuaung.product_catalog_app.core.domain.map
import com.neythuaung.product_catalog_app.product.data.data_source.local.database.ProductDatabase
import com.neythuaung.product_catalog_app.product.data.data_source.remote.ProductApi
import com.neythuaung.product_catalog_app.product.data.model.mapper.toProductList
import com.neythuaung.product_catalog_app.product.domain.entity.FavoriteProductEntity
import com.neythuaung.product_catalog_app.product.domain.entity.ProductList
import com.neythuaung.product_catalog_app.product.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productDatabase: ProductDatabase,
    private val safeApiCall: SafeApiCall
) : ProductRepository {
    override suspend fun getProductList(): Result<ProductList, Error> {
        return safeApiCall {
            productApi.getProductList()
        }.map {
            it.toProductList()
        }
    }

    override suspend fun upsert(product: FavoriteProductEntity): Long =
        productDatabase.getProductDao().insert(product)


    override fun getFavouriteProducts(): LiveData<List<FavoriteProductEntity>> =
        productDatabase.getProductDao().getAllFavorites()


    override suspend fun deleteProduct(product: FavoriteProductEntity) {
        productDatabase.getProductDao().delete(product)
    }

}