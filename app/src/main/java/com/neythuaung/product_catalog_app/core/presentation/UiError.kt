package com.neythuaung.product_catalog_app.core.presentation

import android.util.Log
import com.neythuaung.product_catalog_app.core.domain.Error

enum class UiErrorType {
    NO_INTERNET,
    TIMEOUT,
    SERVER_ERROR,
    UNKNOWN
}

fun Error.toUiErrorType(): UiErrorType {
    return when (this) {

        Error.Network -> UiErrorType.NO_INTERNET

        Error.Timeout -> UiErrorType.TIMEOUT

        Error.Server -> UiErrorType.SERVER_ERROR

        Error.Unknown -> UiErrorType.UNKNOWN

        is Error.Remote -> {
            when (code) {
                400 -> UiErrorType.SERVER_ERROR
                401 -> UiErrorType.SERVER_ERROR
                403 -> UiErrorType.SERVER_ERROR
                404 -> UiErrorType.SERVER_ERROR
                500 -> UiErrorType.SERVER_ERROR
                else -> UiErrorType.UNKNOWN
            }
        }
    }
}

fun UiErrorType.toUserMessage(error: Error? = null): String {
    return when (this) {

        UiErrorType.NO_INTERNET ->
            "No internet connection. Please check your network."

        UiErrorType.TIMEOUT ->
            "Request timeout. Try again."

        UiErrorType.SERVER_ERROR -> {
            Log.d("KHKJHKHKJ", "toUserMessage: $error")
            if (error is Error.Remote && error.message != null) {
                error.message
            } else {
                "Server error. Please try later."
            }
        }

        UiErrorType.UNKNOWN ->
            "Something went wrong"
    }
}