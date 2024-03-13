package com.oybekdev.lokmart.domain.model

import com.oybekdev.lokmart.data.api.product.dto.Category

data class ProductQuery (
    val category:Category? = null
)