package com.oybekdev.lokmart.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oybekdev.lokmart.data.api.product.dto.Category
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.domain.model.ProductQuery
import com.oybekdev.lokmart.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
):ViewModel() {

    val loading = MutableLiveData(false)
    val products = MutableLiveData<PagingData<Product>>()
    val query = MutableLiveData(ProductQuery())
    val recents = MutableLiveData<List<String>>()

    init {
        getRecents()
    }
    private fun getProducts() = viewModelScope.launch{
        productRepository.getProducts(query.value!!).cachedIn(viewModelScope).collect {
            products.postValue(it)
        }
    }

    fun setCategory(category: Category){
        query.postValue(query.value!!.copy(category = category))
        getProducts()
    }

    fun setSearch(search:String){
        query.postValue(query.value!!.copy(search = search))
        addRecents(search)
        getProducts()
    }

    fun setLoadState(states: CombinedLoadStates) {
        val loading = states.source.refresh is LoadState.Loading
        this.loading.postValue(loading)
    }

    private fun getRecents() = viewModelScope.launch {
        productRepository.getRecents().collectLatest {
            recents.postValue(it)
        }
    }

    fun clearRecents() = viewModelScope.launch {
        productRepository.clearRecents()
    }

    private fun addRecents(search:String) = viewModelScope.launch {
        productRepository.addRecent(search)
    }

    fun setQuery(query:ProductQuery){
        this.query.value = query
        getProducts()
    }
}
