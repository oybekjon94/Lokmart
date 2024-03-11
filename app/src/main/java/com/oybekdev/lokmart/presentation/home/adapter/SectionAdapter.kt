package com.oybekdev.lokmart.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.inflate
import com.oybekdev.lokmart.data.api.auth.dto.SectionType
import com.oybekdev.lokmart.data.api.product.dto.Product
import com.oybekdev.lokmart.data.api.product.dto.Section
import com.oybekdev.lokmart.databinding.ItemSectionHorizontalBinding
import com.oybekdev.lokmart.databinding.ItemSectionVerticalBinding

class SectionAdapter(
    private val sections: List<Section>,
    private val showAll: (section: Section) -> Unit,
    private val onClick:(product:Product) -> Unit,
    private val like: (product:Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class HorizontalHolder(private val binding: ItemSectionHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) = with(binding) {
            title.text = section.title
            showAll.setOnClickListener {
                this@SectionAdapter.showAll(section)
            }
            products.adapter = HorizontalAdapter(section.products, onClick, like)
        }
    }

    inner class VerticalHolder(private val binding: ItemSectionVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section)  = with(binding){
            title.text = section.title
            showAll.setOnClickListener {
                this@SectionAdapter.showAll(section)
            }
            products.adapter = HorizontalAdapter(section.products, onClick, like)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (SectionType.values()[viewType]) {
            SectionType.horizontal -> HorizontalHolder(
                ItemSectionHorizontalBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            SectionType.vertical -> VerticalHolder(
                ItemSectionVerticalBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = sections.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalHolder -> holder.bind(sections[position])
            is VerticalHolder -> holder.bind(sections[position])
        }
    }

    override fun getItemViewType(position: Int) = sections[position].type.ordinal
}
