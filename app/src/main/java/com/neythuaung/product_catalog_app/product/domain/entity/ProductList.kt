package com.neythuaung.product_catalog_app.product.domain.entity

data class ProductList(
    val products: List<Product>,
    val total: Long,
    val skip: Long,
    val limit: Long,
)

data class Product(
    val id: Long? = 0,
    val title: String? = "",
    val description: String? = "",
    val category: String? = "",
    val price: Double? = 0.0,
    val discountPercentage: Double? = 0.0,
    val rating: Double? = 0.0,
    val stock: Long? = 0,
    val tags: List<String>? = emptyList(),
    val brand: String? = "",
    val sku: String? = "",
    val weight: Long? = 0,
    val dimensions: Dimensions? = Dimensions(),
    val warrantyInformation: String? = "",
    val shippingInformation: String? = "",
    val availabilityStatus: String? = "",
    val reviews: List<Review>? = emptyList(),
    val returnPolicy: String? = "",
    val minimumOrderQuantity: Long? = 0,
    val meta: Meta? = Meta(),
    val images: List<String>? = emptyList(),
    val thumbnail: String? = "",
    val isFavourite: Boolean? = false
)

data class Dimensions(
    val width: Double? = 0.0,
    val height: Double? = 0.0,
    val depth: Double? = 0.0,
)

data class Review(
    val rating: Long,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String,
)

data class Meta(
    val createdAt: String? = "",
    val updatedAt: String? = "",
    val barcode: String? = "",
    val qrCode: String? = "",
)
