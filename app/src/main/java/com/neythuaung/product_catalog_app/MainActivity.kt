package com.neythuaung.product_catalog_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.neythuaung.product_catalog_app.config.StorageConfig
import com.neythuaung.product_catalog_app.core.utils.AppSharedPreference
import com.neythuaung.product_catalog_app.databinding.ActivityMainBinding
import com.neythuaung.product_catalog_app.databinding.ActivityProductBinding
import com.neythuaung.product_catalog_app.login.presentation.LoginActivity
import com.neythuaung.product_catalog_app.product.presentation.ProductActivity
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val appSharedPreference: AppSharedPreference by lazy {
        AppSharedPreference(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isUserLoggedIn()) {
            goToProduct()
        } else {
            goToLogin()
        }

    }

    private fun isUserLoggedIn(): Boolean {
        val token = appSharedPreference.getString(StorageConfig.TOKEN)
        Log.d("UHKJHKJHK", "isUserLoggedIn: $token")
        return !token.isNullOrEmpty()
    }

    private fun goToProduct() {
        startActivity(Intent(this, ProductActivity::class.java))
        finish()
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


}