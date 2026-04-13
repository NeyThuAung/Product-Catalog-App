package com.neythuaung.product_catalog_app.login.presentation.fragments.ui_state

import com.neythuaung.product_catalog_app.login.domain.entity.Login

data class LoginState(
    val isLoading: Boolean = false,
    val success: Login = Login()
)