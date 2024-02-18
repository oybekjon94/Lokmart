package com.oybekdev.lokmart.domain.repo

import com.oybekdev.lokmart.domain.model.User

interface AuthRepository {

    suspend fun signIn(username:String,password:String):User
}