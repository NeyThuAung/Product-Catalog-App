package com.neythuaung.product_catalog_app.product.data.data_source.remote

import com.neythuaung.product_catalog_app.product.data.model.ProductResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

    @GET("products")
    suspend fun getProductList(): Response<ProductResponseDto>

}