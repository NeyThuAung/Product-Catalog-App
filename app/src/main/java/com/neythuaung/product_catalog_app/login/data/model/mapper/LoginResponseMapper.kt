package com.neythuaung.product_catalog_app.login.data.model.mapper

import com.neythuaung.product_catalog_app.login.data.model.response.LoginResponseDto
import com.neythuaung.product_catalog_app.login.domain.entity.Login

fun LoginResponseDto.toLogin(): Login {
    return Login(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        image = image,
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}