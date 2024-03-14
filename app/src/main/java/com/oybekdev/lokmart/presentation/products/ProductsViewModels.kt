package com.oybekdev.lokmart.presentation.products

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.oybekdev.lokmart.data.api.product.dto.Category
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.domain.model.ProductQuery
import com.oybekdev.lokmart.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModels @Inject constructor(
    private val productRepository: ProductRepository
):ViewModel(){

    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)
    val products = MediatorLiveData<PagingData<Product>>()
    val category = MutableLiveData<Category>()

    fun setCategory(category: Category){
        this.category.postValue(category)

    }

    fun getProducts() = viewModelScope.launch{
        val query = ProductQuery(category = category.value)
        val products = productRepository.getProducts(query)
        this@ProductsViewModels.products.addSource(products){
            this@ProductsViewModels.products.postValue(it)
        }
    }

    fun setLoadStates(states:CombinedLoadStates){
        val loading = states.source.refresh is LoadState.Loading
        this.loading.postValue(loading)
    }
}