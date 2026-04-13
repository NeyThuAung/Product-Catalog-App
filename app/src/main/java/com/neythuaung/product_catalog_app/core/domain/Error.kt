package com.neythuaung.product_catalog_app.core.domain


sealed class Error {
    data class Remote(
        val code: Int,
        val message: String? = null
    ) : Error()

    object Network : Error()
    object Timeout : Error()
    object Server : Error()
    object Unknown : Error()
}