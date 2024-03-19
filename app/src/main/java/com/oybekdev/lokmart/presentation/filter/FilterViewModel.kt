package com.oybekdev.lokmart.presentation.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oybekdev.lokmart.data.api.product.dto.Category
import com.oybekdev.lokmart.domain.repo.ProductRepository
import com.oybekdev.lokmart.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

class FilterViewModel @Inject constructor(
    private val productRepository: ProductRepository
):ViewModel() {

    val categories = MutableLiveData<List<Category>>()
    val events = SingleLiveEvent<Event>()

    fun getCategories() = viewModelScope.launch {
        try {
            val result = productRepository.getCategories()
            categories.postValue(result)
        }catch (e:Exception){
            events.postValue(Event.CategoriesError)
        }
    }

    sealed class Event{
        object CategoriesError:Event()
    }
}