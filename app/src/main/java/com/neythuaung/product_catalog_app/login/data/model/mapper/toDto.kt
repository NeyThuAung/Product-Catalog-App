package com.neythuaung.product_catalog_app.login.data.model.mapper

import com.neythuaung.product_catalog_app.login.data.model.request.LoginRequestDto
import com.neythuaung.product_catalog_app.login.domain.entity.LoginRequest

fun LoginRequest.toDto(): LoginRequestDto {
    return LoginRequestDto(
        username = username.toString(),
        password = password.toString(),
        expiresInMins = expiresInMins
    )
}