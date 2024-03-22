package com.oybekdev.lokmart.data.api.product

import com.oybekdev.lokmart.data.api.product.dto.Category
import com.oybekdev.lokmart.data.api.product.dto.HomeResponse
import com.oybekdev.lokmart.data.api.product.dto.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("home")
    suspend fun getHome():HomeResponse

    @GET("categories")
    suspend fun getCategories():List<Category>

    @GET("products")
    suspend fun getProducts(
        @Query("categories_id") categoryId:String?,
        @Query("search") search:String?,
        @Query("from") from :Int?,
        @Query("to") to :Int?,
        @Query("rating") rating :Int?,
        @Query("discount") discount :Int?,
        @Query("sort") sort :String?,
        @Query("page") page:Int,
        @Query("size") size:Int
    ):List<Product>
}
//1:13:24