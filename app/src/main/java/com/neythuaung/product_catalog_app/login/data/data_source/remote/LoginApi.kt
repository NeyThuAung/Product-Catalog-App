package com.neythuaung.product_catalog_app.login.data.data_source.remote

import com.neythuaung.product_catalog_app.login.data.model.request.LoginRequestDto
import com.neythuaung.product_catalog_app.login.data.model.response.LoginResponseDto
import com.neythuaung.product_catalog_app.product.data.model.ProductResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<LoginResponseDto>
}