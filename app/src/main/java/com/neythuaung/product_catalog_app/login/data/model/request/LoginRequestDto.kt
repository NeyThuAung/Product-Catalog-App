package com.neythuaung.product_catalog_app.login.data.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("expiresInMins")
    val expiresInMins: Int = 60 // default like API
)