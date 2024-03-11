package com.oybekdev.lokmart.presentation.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oybekdev.lokmart.domain.model.User
import com.oybekdev.lokmart.domain.repo.AuthRepository
import com.oybekdev.lokmart.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel(){

    val loading = MutableLiveData(false)
    val events = SingleLiveEvent<Event>()

    fun signUp(username:String,email:String,password:String) =
        viewModelScope.launch ( Dispatchers.IO ){
           loading.postValue(true)
           try {
               authRepository.signUp(username,email,password)
               events.postValue(Event.NavigateHome)
           }catch (e:Exception){
               when{
                   e is HttpException && e.code() == 403 -> events.postValue(Event.AlreadyRegistered)
                   e is IOException -> events.postValue(Event.ConnectionError)
                   else -> events.postValue(Event.Error)
               }
           }finally {
               loading.postValue(false)
           }
    }

    sealed class Event{
        object AlreadyRegistered:Event()
        object ConnectionError:Event()
        object Error:Event()
        object NavigateHome:Event()
    }
}