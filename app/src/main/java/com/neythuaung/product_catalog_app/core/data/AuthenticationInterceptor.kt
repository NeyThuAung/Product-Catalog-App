package com.neythuaung.product_catalog_app.core.data

import android.util.Log
import com.neythuaung.product_catalog_app.config.StorageConfig
import com.neythuaung.product_catalog_app.core.utils.AppSharedPreference
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthenticationInterceptor @Inject constructor(
    private val sharedPreference: AppSharedPreference
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token: String? = sharedPreference.getString(StorageConfig.TOKEN)

        return try {
            if (!token.isNullOrBlank()) {
                Log.d("ApiToken", "intercept: ${token.take(10)}...")
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    //.addHeader("Accept", "application/json")
                    //.addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(originalRequest)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            chain.proceed(originalRequest)
        }
    }
}