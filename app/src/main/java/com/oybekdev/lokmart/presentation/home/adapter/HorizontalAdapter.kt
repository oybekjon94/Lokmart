package com.oybekdev.lokmart.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oybekdev.lokmart.R
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.databinding.ItemProductHorizontalSectionBinding
import kotlin.math.roundToInt

class HorizontalAdapter(
    private val products: List<Product>,
    private val onClick: (product: Product) -> Unit,
    private val like: (product:Product) -> Unit
) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductHorizontalSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            Glide.with(root).load(product.image).into(image)

            discount.isVisible = product.discount != null
            product.discount?.let {
                val percent = (product.discount / product.price * 100).roundToInt()
                binding.discount.text =
                    root.context.getString(R.string.fragment_item_product_discount, percent)


                name.text = product.title
                rating.text = String.format("%.1f",product.rating)
                ratingCount.text =
                    root.context.getString(R.string.item_product_ratings_count,product.ratingCount)

                val current = product.price - (product.discount ?: 0.0)
                price.text = root.context.getString(R.string.price,product.price)
                old.text = root.context.getString(R.string.price_striked, product.price)
                old.isVisible = product.discount != null

                root.setOnClickListener{
                    onClick(product)
                }

                fun setLike(){
                    val liked  =
                        if (product.favourite)R.drawable.ic_heart_checked else R.drawable.ic_heart_unchecked
                    like.setImageResource(liked)
                }
                setLike()

                like.setOnClickListener{
                    product.favourite = product.favourite.not()
                    setLike()
                    like(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  ViewHolder (
        ItemProductHorizontalSectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }
}