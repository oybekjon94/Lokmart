package com.oybekdev.lokmart.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oybekdev.lokmart.data.api.product.dto.Detail
import com.oybekdev.lokmart.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(
    private val productRepository: ProductRepository
):ViewModel(){

    var loading = MutableLiveData(false)
    var error = MutableLiveData(false)
    var detail = MutableLiveData<Detail>()

    fun getProduct(id:String) = viewModelScope.launch {
        loading.postValue(true)
        try {
            val result = productRepository.getProduct(id)
            detail.postValue(result)
        }catch (e:Exception){
            error.postValue(true)
        }finally {
            loading.postValue(false)
        }
    }
}