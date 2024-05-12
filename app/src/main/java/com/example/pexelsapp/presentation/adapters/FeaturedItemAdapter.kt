package com.example.pexelsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pexelsapp.databinding.FeaturedItemBinding
import com.example.pexelsapp.domain.models.Featured
import com.example.pexelsapp.presentation.adapters.viewHolders.FeaturedItemViewHolder
import java.lang.Integer.min
import javax.inject.Inject

class FeaturedItemAdapter() :
    RecyclerView.Adapter<FeaturedItemViewHolder>() {

    lateinit var onItemClick: ((Featured) -> Unit)
    private var featuredItemsList = ArrayList<Featured>()
    private var selectedPosition = RecyclerView.NO_POSITION

    fun setFeatured(featuredItemsList: ArrayList<Featured>) {
        val previousSize = itemCount
        val newSize = featuredItemsList.size

        for (i in 0 until min(previousSize, newSize)) {
            if (featuredItemsList[i] != this.featuredItemsList[i]) {
                notifyItemChanged(i)
            }
        }

        if (newSize > previousSize) {
            notifyItemRangeInserted(previousSize, newSize - previousSize)
        }

        this.featuredItemsList = featuredItemsList
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

    override fun getItemCount(): Int = featuredItemsList.size

    override fun onBindViewHolder(holder: FeaturedItemViewHolder, position: Int) {
        val featuredItem = featuredItemsList[position]
        holder.binding.featuredItem.text = featuredItem.title
        holder.itemView.isSelected = holder.adapterPosition == selectedPosition

        holder.itemView.setOnClickListener {
            val clickedPosition = holder.adapterPosition
            if (clickedPosition != RecyclerView.NO_POSITION) {
                val previouslySelectedPosition = selectedPosition
                selectedPosition = if (clickedPosition == selectedPosition) {
                    RecyclerView.NO_POSITION
                } else {
                    clickedPosition
                }
                notifyItemChanged(previouslySelectedPosition)
                notifyItemChanged(selectedPosition)
                onItemClick.invoke(featuredItem)
            }
        }
    }
}