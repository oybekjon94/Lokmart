package com.oybekdev.lokmart.domain.repo

import com.oybekdev.lokmart.data.api.product.dto.Category
import com.oybekdev.lokmart.data.api.product.dto.HomeResponse

interface ProductRepository {
    suspend fun getHome():HomeResponse
    suspend fun getCategories():List<Category>
}