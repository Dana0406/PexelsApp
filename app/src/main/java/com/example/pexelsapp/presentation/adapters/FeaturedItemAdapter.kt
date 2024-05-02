package com.example.pexelsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pexelsapp.databinding.FeaturedItemBinding
import com.example.pexelsapp.domain.models.Featured
import javax.inject.Inject

class FeaturedItemAdapter @Inject constructor() : RecyclerView.Adapter<FeaturedItemAdapter.FeaturedItemViewHolder>() {
    lateinit var onItemClick:((Featured) -> Unit)
    private var featuredItemsList = ArrayList<Featured>()
    private var selectedPosition = RecyclerView.NO_POSITION

    fun setFeatured(featuredItemsList: ArrayList<Featured>) {
        this.featuredItemsList = featuredItemsList
        selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }

    fun clearSelection() {
        val previousSelectedPosition = selectedPosition
        selectedPosition = RecyclerView.NO_POSITION
        notifyItemChanged(previousSelectedPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedItemViewHolder {
        return FeaturedItemViewHolder(
            FeaturedItemBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false
                )
        )
    }

    override fun getItemCount(): Int {
        return featuredItemsList.size
    }

    override fun onBindViewHolder(holder: FeaturedItemViewHolder, position: Int) {
        val featuredItem = featuredItemsList[position]
        holder.binding.featuredItem.text = featuredItem.title.toString()
        holder.itemView.isSelected = holder.adapterPosition == selectedPosition

        holder.itemView.setOnClickListener {
            val clickedPosition = holder.adapterPosition
            if (clickedPosition != RecyclerView.NO_POSITION) {
                if (clickedPosition == selectedPosition) {
                    selectedPosition = RecyclerView.NO_POSITION
                } else {
                    selectedPosition = clickedPosition
                }
                notifyDataSetChanged()
                onItemClick.invoke(featuredItem)
            }
        }
    }

    class FeaturedItemViewHolder(val binding: FeaturedItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}