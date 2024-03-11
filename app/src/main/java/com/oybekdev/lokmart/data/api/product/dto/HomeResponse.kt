package com.oybekdev.lokmart.data.api.product.dto

import com.google.gson.annotations.SerializedName
import com.oybekdev.lokmart.data.api.auth.dto.UserDto


data class HomeResponse(
    @SerializedName("banners")
    val banners: List<Banner>,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("sections")
    val sections: List<Section>,
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: UserDto,
    @SerializedName("notification_count")
    val notificationCount:Int
)