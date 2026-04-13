package com.neythuaung.product_catalog_app.login.domain.entity

data class LoginRequest(
    var username: String? = "",
    var password: String? = "",
    val expiresInMins: Int = 60
)