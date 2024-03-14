package com.oybekdev.lokmart.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.oybekdev.lokmart.data.api.product.ProductApi
import com.oybekdev.lokmart.data.api.product.dto.Category
import com.oybekdev.lokmart.data.api.product.dto.HomeResponse
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.data.api.product.paging.ProductPagingSource
import com.oybekdev.lokmart.data.store.RecentStore
import com.oybekdev.lokmart.data.store.UserStore
import com.oybekdev.lokmart.domain.model.ProductQuery
import com.oybekdev.lokmart.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val userStore:UserStore,
    private val recentStore: RecentStore
):ProductRepository {
    override suspend fun getHome(): HomeResponse {
        val response = productApi.getHome()
        userStore.set(response.user)
        return response
    }

    override suspend fun getCategories() = productApi.getCategories()
    override suspend fun getProducts(query: ProductQuery):LiveData<PagingData<Product>>{
        query.search?.let {
            val recents = (recentStore.get() ?: emptyArray()).toMutableList()
            recents.add(0,it)
            recentStore.set(recents.toTypedArray())
        }

        return Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 10,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        initialKey = 0,
        pagingSourceFactory = {
            ProductPagingSource(productApi, query)
        }
    ).liveData
    }

    override fun getRecents() = recentStore.getFlow().filterNotNull().map { it.toList() }
    override suspend fun clearRecents() = recentStore.clear()
}
