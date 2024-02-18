package com.oybekdev.lokmart.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.oybekdev.lokmart.data.api.auth.dto.UserDto
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStore @Inject constructor(private val dataStore: DataStore<Preferences>,private val gson: Gson) {
    private val key = stringPreferencesKey("user")

    suspend fun set(value:UserDto){
        dataStore.edit {
            it[key] = gson.toJson(value)
        }
    }

    suspend fun get():UserDto? = dataStore.data.map { it[key] }.firstOrNull()?.let {
        gson.fromJson(it,UserDto::class.java)
    }

    suspend fun clear(){
        dataStore.edit {
            it.remove(key)
        }
    }
}