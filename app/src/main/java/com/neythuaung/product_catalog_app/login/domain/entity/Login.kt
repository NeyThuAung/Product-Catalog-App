package com.neythuaung.product_catalog_app.login.domain.entity

data class Login(
    val id: Long? = 0,
    val username: String? = "",
    val email: String? = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val gender: String? = "",
    val image: String? = "",
    val accessToken: String? = "",
    val refreshToken: String? = "",
)
