package com.oybekdev.lokmart.data.repo

import com.oybekdev.lokmart.data.api.product.ProductApi
import com.oybekdev.lokmart.data.api.product.dto.Category
import com.oybekdev.lokmart.data.api.product.dto.HomeResponse
import com.oybekdev.lokmart.data.store.UserStore
import com.oybekdev.lokmart.domain.repo.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val userStore:UserStore
):ProductRepository {
    override suspend fun getHome(): HomeResponse {
        val response = productApi.getHome()
        userStore.set(response.user)
        return response
    }

    override suspend fun getCategories() = productApi.getCategories()
}