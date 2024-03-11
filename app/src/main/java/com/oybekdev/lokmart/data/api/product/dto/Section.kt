package com.oybekdev.lokmart.data.api.product.dto

import com.google.gson.annotations.SerializedName
import com.oybekdev.lokmart.data.api.auth.dto.SectionType

data class Section(
    @SerializedName("id")
    val id: String,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: SectionType
)