package com.oybekdev.lokmart.presentation.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.databinding.ItemProductBinding

class ProductsAdapter(
    private val onClick:(product:Product) -> Unit,
    private val like:(product:Product) -> Unit
):PagingDataAdapter<Product,ProductViewHolder>(DIFF_UTIL) {

    companion object{
        private val DIFF_UTIL = object :DiffUtil.ItemCallback<Product>(){
            override fun areItemsTheSame(oldItem: Product, newItem: Product)=oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return, onClick,like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder (
        ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )


}