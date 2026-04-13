package com.neythuaung.product_catalog_app.login.presentation.utils

import android.content.Context
import android.content.Intent
import com.neythuaung.product_catalog_app.product.presentation.ProductActivity

fun Context.goToProductActivity() {
    val intent = Intent(
        this, ProductActivity::class.java
    )
    startActivity(intent)
}