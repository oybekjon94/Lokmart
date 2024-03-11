package com.oybekdev.lokmart.data.api.product.dto


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("username")
    val username: String
)

//20:43