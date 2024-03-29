package com.oybekdev.lokmart.data.repo

import com.oybekdev.lokmart.data.api.auth.AuthApi
import com.oybekdev.lokmart.data.api.auth.dto.AuthResponse
import com.oybekdev.lokmart.data.api.auth.dto.SignInRequest
import com.oybekdev.lokmart.data.api.auth.dto.SignUpRequest
import com.oybekdev.lokmart.data.store.OnboardedStore
import com.oybekdev.lokmart.data.store.TokenStore
import com.oybekdev.lokmart.data.store.UserStore
import com.oybekdev.lokmart.domain.model.Destination
import com.oybekdev.lokmart.domain.model.User
import com.oybekdev.lokmart.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi:AuthApi,
    private val tokenStore: TokenStore,
    private val userStore: UserStore,
    private val onboardedStore:OnboardedStore
):AuthRepository {
    override suspend fun signUp(username: String, password: String) {
        val request = SignInRequest(username,password)
        val response = authApi.signIn(request)
        saveUserInfo(response)
    }

    override suspend fun signUp(username: String, email: String, password: String) {
        val request = SignUpRequest(username,email,password)
        val response = authApi.signUp(request)
        saveUserInfo(response)
    }

    override fun destinationFlow() = channelFlow {
        suspend fun sendDestination(){
            when{
                tokenStore.get() != null -> send(Destination.Home)
                onboardedStore.get() == true -> send(Destination.Auth)
                else -> send(Destination.Onboarding)
            }
        }

        launch {
            tokenStore.getFlow().collectLatest {
                sendDestination()
            }
        }

        launch {
            onboardedStore.getFlow().collectLatest {
                sendDestination()
            }
        }
    }.distinctUntilChanged()

    override suspend fun onboarded() = onboardedStore.set(true)

    private suspend fun saveUserInfo(response:AuthResponse){
        tokenStore.set(response.token)
        userStore.set(response.user)
    }
}