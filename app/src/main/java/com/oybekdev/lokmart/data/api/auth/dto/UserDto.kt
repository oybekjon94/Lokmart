package com.oybekdev.lokmart.data.api.auth.dto

import com.google.gson.annotations.SerializedName
import com.oybekdev.lokmart.domain.model.User

data class UserDto(
    @SerializedName("username")
    val username:String
){
    fun toUser() = User(
        username = username
    )
}
