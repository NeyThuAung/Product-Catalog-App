package com.neythuaung.product_catalog_app.product.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val brand: String?,
    val price: Double,
    val thumbnail: String
)
