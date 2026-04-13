package com.neythuaung.product_catalog_app.product.data.data_source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.neythuaung.product_catalog_app.product.domain.entity.FavoriteProductEntity

@Dao
interface FavoriteProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: FavoriteProductEntity): Long

    @Delete
    suspend fun delete(product: FavoriteProductEntity)

    @Query("SELECT * FROM favorite_products")
    fun getAllFavorites(): LiveData<List<FavoriteProductEntity>>

}