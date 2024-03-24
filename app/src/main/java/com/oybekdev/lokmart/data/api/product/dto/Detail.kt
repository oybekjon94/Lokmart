package com.oybekdev.lokmart.data.api.product.dto


import com.google.gson.annotations.SerializedName

data class Detail(
    val category: String,
    val description: String,
    val discount: Int,
    val favorite: Boolean,
    val id: String,
    val images: List<String>,
    @SerializedName("in_stock")
    val inStock: Int,
    val price: Int,
    val rating: Double,
    val related: List<Product>,
    val reviews: Int,
    val title: String
)