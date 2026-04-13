package com.neythuaung.product_catalog_app.login.data.repository

import android.util.Log
import com.neythuaung.product_catalog_app.core.data.SafeApiCall
import com.neythuaung.product_catalog_app.core.domain.Error
import com.neythuaung.product_catalog_app.core.domain.Result
import com.neythuaung.product_catalog_app.core.domain.map
import com.neythuaung.product_catalog_app.login.data.data_source.remote.LoginApi
import com.neythuaung.product_catalog_app.login.data.model.mapper.toDto
import com.neythuaung.product_catalog_app.login.data.model.mapper.toLogin
import com.neythuaung.product_catalog_app.login.data.model.request.LoginRequestDto
import com.neythuaung.product_catalog_app.login.domain.entity.Login
import com.neythuaung.product_catalog_app.login.domain.entity.LoginRequest
import com.neythuaung.product_catalog_app.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val safeApiCall: SafeApiCall
) : LoginRepository {
    override suspend fun login(request: LoginRequestDto): Result<Login, Error> {
        Log.d("HKHKJHKJH", "login: $request")
        return safeApiCall{
            loginApi.login(request)
        }.map {
            it.toLogin()
        }
    }
}