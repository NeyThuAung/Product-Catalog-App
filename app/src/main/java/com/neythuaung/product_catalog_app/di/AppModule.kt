package com.neythuaung.product_catalog_app.di

import android.app.Application
import com.neythuaung.product_catalog_app.config.ApiConfig
import com.neythuaung.product_catalog_app.core.data.AuthenticationInterceptor
import com.neythuaung.product_catalog_app.core.data.SafeApiCall
import com.neythuaung.product_catalog_app.core.utils.AppSharedPreference
import com.neythuaung.product_catalog_app.login.data.data_source.remote.LoginApi
import com.neythuaung.product_catalog_app.login.data.repository.LoginRepositoryImpl
import com.neythuaung.product_catalog_app.login.domain.repository.LoginRepository
import com.neythuaung.product_catalog_app.product.data.data_source.local.database.ProductDatabase
import com.neythuaung.product_catalog_app.product.data.data_source.remote.ProductApi
import com.neythuaung.product_catalog_app.product.data.repository.ProductRepositoryImpl
import com.neythuaung.product_catalog_app.product.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    @Named("ProductApi")
    fun provideNewsApi(
        sharedPreference: AppSharedPreference
    ): ProductApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(sharedPreference))
            .addInterceptor(logging)
            .retryOnConnectionFailure(false)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(ApiConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    @Named("LoginApi")
    fun provideLoginApi(): LoginApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .retryOnConnectionFailure(false)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(ApiConfig.AUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductDatabase(application: Application): ProductDatabase =
        ProductDatabase(application)

    @Provides
    @Singleton
    fun provideProductRepository(
        @Named("ProductApi") productApi: ProductApi,
        productDatabase: ProductDatabase,
        safeApiCall: SafeApiCall
    ): ProductRepository {
        return ProductRepositoryImpl(productApi, productDatabase, safeApiCall)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        @Named("LoginApi") loginApi: LoginApi,
        safeApiCall: SafeApiCall
    ): LoginRepository {
        return LoginRepositoryImpl(loginApi,safeApiCall)
    }


}