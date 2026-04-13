package com.neythuaung.product_catalog_app.login.domain.usecase

import com.neythuaung.product_catalog_app.core.domain.Error
import com.neythuaung.product_catalog_app.core.domain.Result
import com.neythuaung.product_catalog_app.login.data.model.mapper.toDto
import com.neythuaung.product_catalog_app.login.domain.entity.Login
import com.neythuaung.product_catalog_app.login.domain.entity.LoginRequest
import com.neythuaung.product_catalog_app.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(logInRequest: LoginRequest): Result<Login, Error> {
        return loginRepository.login(logInRequest.toDto())
    }
}