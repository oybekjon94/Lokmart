package com.oybekdev.lokmart.domain.repo

import android.graphics.pdf.PdfDocument.Page
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.oybekdev.lokmart.data.api.product.dto.Category
import com.oybekdev.lokmart.data.api.product.dto.HomeResponse
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.domain.model.ProductQuery
import java.util.concurrent.Flow

interface ProductRepository {
    suspend fun getHome():HomeResponse
    suspend fun getCategories():List<Category>
    fun getProducts(query: ProductQuery):kotlinx.coroutines.flow.Flow<PagingData<Product>>
    fun getRecents(): kotlinx.coroutines.flow.Flow<List<String>>

    suspend fun clearRecents()

    suspend fun addRecent(search:String)
}