package com.oybekdev.lokmart.domain.repo

import com.oybekdev.lokmart.domain.model.Destination
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    suspend fun signUp(username:String, password:String)
    suspend fun signUp(username:String, email:String, password:String)
    fun destinationFlow():Flow<Destination>
    suspend fun onboarded()

}