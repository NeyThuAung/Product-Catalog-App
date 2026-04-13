package com.neythuaung.product_catalog_app.login.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neythuaung.product_catalog_app.core.domain.onError
import com.neythuaung.product_catalog_app.core.domain.onSuccess
import com.neythuaung.product_catalog_app.core.presentation.toUiErrorType
import com.neythuaung.product_catalog_app.core.presentation.toUserMessage
import com.neythuaung.product_catalog_app.core.utils.showToast
import com.neythuaung.product_catalog_app.login.domain.entity.Login
import com.neythuaung.product_catalog_app.login.domain.entity.LoginRequest
import com.neythuaung.product_catalog_app.login.domain.usecase.LoginUseCase
import com.neythuaung.product_catalog_app.login.presentation.fragments.ui_state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val application: Application
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState
    fun login(userName: String, password: String, onError: (String) -> Unit) {
        val loginRequest = LoginRequest(
            username = userName,
            password = password,
        )
        _loginState.update {
            it.copy(
                isLoading = true,
                success = Login()
            )
        }
        viewModelScope.launch {
            loginUseCase(loginRequest)
                .onSuccess { data ->
                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            success = data
                        )
                    }
                }
                .onError { dataError ->
                    val message = dataError
                        .toUiErrorType()
                        .toUserMessage(dataError)

                    onError(message)

//                    application.showToast(message)
                }
        }

    }


}