package com.oybekdev.lokmart.data.repo

import com.oybekdev.lokmart.data.api.auth.AuthApi
import com.oybekdev.lokmart.data.api.auth.dto.SignInRequest
import com.oybekdev.lokmart.data.store.TokenStore
import com.oybekdev.lokmart.data.store.UserStore
import com.oybekdev.lokmart.domain.model.User
import com.oybekdev.lokmart.domain.repo.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi:AuthApi,
    private val tokenStore: TokenStore,
    private val userStore: UserStore
):AuthRepository {
    override suspend fun signIn(username: String, password: String):User {
        val request = SignInRequest(username,password)
        val response = authApi.signIn(request)
        tokenStore.set(response.token)
        userStore.set(response.user)
        return response.user.toUser()
    }
}