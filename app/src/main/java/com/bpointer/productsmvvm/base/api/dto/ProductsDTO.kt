package com.bpointer.productsmvvm.base.api.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductsDTO(
    @Json(name = "products")
    val products: List<Product> = listOf()
) {
    @JsonClass(generateAdapter = true)
    data class Product(
        @Json(name = "addToCartButtonText")
        val addToCartButtonText: String = "",
        @Json(name = "badges")
        val badges: List<String> = listOf(),
        @Json(name = "brand")
        val brand: String = "",
        @Json(name = "citrusId")
        val citrusId: String = "",
        @Json(name = "id")
        val id: String = "",
        @Json(name = "imageURL")
        val imageURL: String = "",
        @Json(name = "isAddToCartEnable")
        val isAddToCartEnable: Boolean = false,
        @Json(name = "isDeliveryOnly")
        val isDeliveryOnly: Boolean = false,
        @Json(name = "isDirectFromSupplier")
        val isDirectFromSupplier: Boolean = false,
        @Json(name = "isFindMeEnable")
        val isFindMeEnable: Boolean = false,
        @Json(name = "isInTrolley")
        var isInTrolley: Boolean = false,
        @Json(name = "isInWishlist")
        var isInWishlist: Boolean = false,
        @Json(name = "messages")
        val messages: Messages = Messages(),
        @Json(name = "price")
        val price: List<Price> = listOf(),
        @Json(name = "purchaseTypes")
        val purchaseTypes: List<PurchaseType> = listOf(),
        @Json(name = "ratingCount")
        val ratingCount: Double = 0.0,
        @Json(name = "saleUnitPrice")
        val saleUnitPrice: Double = 0.0,
        @Json(name = "title")
        val title: String = "",
        @Json(name = "totalReviewCount")
        val totalReviewCount: Int = 0
    ) {
        @JsonClass(generateAdapter = true)
        data class Messages(
            @Json(name = "promotionalMessage")
            val promotionalMessage: String = "",
            @Json(name = "sash")
            val sash: Sash = Sash(),
            @Json(name = "secondaryMessage")
            val secondaryMessage: String = ""
        ) {
            @JsonClass(generateAdapter = true)
            class Sash
        }

        @JsonClass(generateAdapter = true)
        data class Price(
            @Json(name = "isOfferPrice")
            val isOfferPrice: Boolean = false,
            @Json(name = "message")
            val message: String = "",
            @Json(name = "value")
            val value: Double = 0.0
        )

        @JsonClass(generateAdapter = true)
        data class PurchaseType(
            @Json(name = "cartQty")
            val cartQty: Int = 0,
            @Json(name = "displayName")
            val displayName: String = "",
            @Json(name = "maxQtyLimit")
            val maxQtyLimit: Int = 0,
            @Json(name = "minQtyLimit")
            val minQtyLimit: Int = 0,
            @Json(name = "purchaseType")
            val purchaseType: String = "",
            @Json(name = "unitPrice")
            val unitPrice: Double = 0.0
        )
    }
}