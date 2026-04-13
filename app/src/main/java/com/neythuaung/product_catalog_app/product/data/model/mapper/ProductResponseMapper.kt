package com.neythuaung.product_catalog_app.product.data.model.mapper

import com.neythuaung.product_catalog_app.product.data.model.DimensionsDto
import com.neythuaung.product_catalog_app.product.data.model.MetaDto
import com.neythuaung.product_catalog_app.product.data.model.ProductDto
import com.neythuaung.product_catalog_app.product.data.model.ProductResponseDto
import com.neythuaung.product_catalog_app.product.data.model.ReviewDto
import com.neythuaung.product_catalog_app.product.domain.entity.Dimensions
import com.neythuaung.product_catalog_app.product.domain.entity.Meta
import com.neythuaung.product_catalog_app.product.domain.entity.Product
import com.neythuaung.product_catalog_app.product.domain.entity.ProductList
import com.neythuaung.product_catalog_app.product.domain.entity.Review

fun ProductResponseDto.toProductList(): ProductList {
    return ProductList(
        products = products.map { it.toProduct() },
        total = total,
        skip = skip,
        limit = limit
    )
}

private fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        tags = tags,
        brand = brand,
        sku = sku,
        weight = weight,
        dimensions = dimensions.toDimensions(),
        warrantyInformation = warrantyInformation,
        shippingInformation = shippingInformation,
        availabilityStatus = availabilityStatus,
        reviews = reviews.map { it.toReview() },
        returnPolicy = returnPolicy,
        minimumOrderQuantity = minimumOrderQuantity,
        meta = meta.toMeta(),
        images = images,
        thumbnail = thumbnail,
        isFavourite = false
    )
}

private fun DimensionsDto.toDimensions(): Dimensions {
    return Dimensions(
        width = width,
        height = height,
        depth = depth
    )
}

private fun ReviewDto.toReview(): Review {
    return Review(
        rating = rating,
        comment = comment,
        date = date,
        reviewerName = reviewerName,
        reviewerEmail = reviewerEmail
    )
}

private fun MetaDto.toMeta(): Meta {
    return Meta(
        createdAt = createdAt,
        updatedAt = updatedAt,
        barcode = barcode,
        qrCode = qrCode
    )
}