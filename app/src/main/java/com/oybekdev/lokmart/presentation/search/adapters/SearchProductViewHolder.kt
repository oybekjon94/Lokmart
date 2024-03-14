package com.oybekdev.lokmart.presentation.search.adapters

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oybekdev.lokmart.R
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.databinding.ItemProductSearchBinding

class SearchProductViewHolder(private val binding: ItemProductSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        onClick: (product: Product) -> Unit,
        like: (product: Product) -> Unit
    ) = with(binding) {
        Glide.with(root).load(product.image).into(image)

            name.text = product.title
            rating.text = String.format("%.1f", product.rating)
            ratingCount.text =
                root.context.getString(R.string.item_product_ratings_count, product.ratingCount)

            val current = product.price - (product.discount ?: 0.0)
            price.text = root.context.getString(R.string.price, product.price)
            old.text = root.context.getString(R.string.price_striked, product.price)
            old.isVisible = product.discount != null

            root.setOnClickListener {
                onClick(product)
            }

    }
}

