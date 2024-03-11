package com.oybekdev.lokmart.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oybekdev.lokmart.domain.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {

    fun onboarded() = viewModelScope.launch {
        authRepository.onboarded()
    }
}