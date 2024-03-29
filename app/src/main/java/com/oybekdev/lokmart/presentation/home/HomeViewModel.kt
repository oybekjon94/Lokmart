package com.oybekdev.lokmart.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oybekdev.lokmart.data.api.product.dto.HomeResponse
import com.oybekdev.lokmart.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
):ViewModel() {

    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)
    val home = MutableLiveData<HomeResponse?>(null)

    init {
        getHome()
    }

     fun getHome() = viewModelScope.launch {
        loading.postValue(true)
        error.postValue(false)
        try {
            val response = productRepository.getHome()
            home.postValue(response)
        }catch (e:Exception){
            error.postValue(true)
        }finally {
            loading.postValue(false)
        }
    }
    sealed class Event{

    }
}