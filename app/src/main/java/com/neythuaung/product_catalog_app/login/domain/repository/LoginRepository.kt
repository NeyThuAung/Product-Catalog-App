package com.neythuaung.product_catalog_app.login.domain.repository

import com.neythuaung.product_catalog_app.core.domain.Error
import com.neythuaung.product_catalog_app.core.domain.Result
import com.neythuaung.product_catalog_app.login.data.model.request.LoginRequestDto
import com.neythuaung.product_catalog_app.login.data.model.response.LoginResponseDto
import com.neythuaung.product_catalog_app.login.domain.entity.Login
import com.neythuaung.product_catalog_app.login.domain.entity.LoginRequest

interface LoginRepository {
    suspend fun login(request: LoginRequestDto): Result<Login, Error>
}