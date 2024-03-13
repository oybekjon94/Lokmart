package com.oybekdev.lokmart.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.databinding.ItemProductBinding
import com.oybekdev.lokmart.presentation.products.ProductViewHolder

class VerticalAdapter(
    private val products: List<Product>,
    private val onClick: (product: Product) -> Unit,
    private val like: (product:Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  ProductViewHolder (
        ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], onClick,like)
    }
}