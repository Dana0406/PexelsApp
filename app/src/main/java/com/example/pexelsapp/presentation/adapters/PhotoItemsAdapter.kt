package com.example.pexelsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pexelsapp.R
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.databinding.ImageItemBinding
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.presentation.adapters.viewHolders.PhotoItemViewHolder
import java.lang.Integer.min
import javax.inject.Inject

class PhotoItemsAdapter() :
    RecyclerView.Adapter<PhotoItemViewHolder>() {

    lateinit var onItemClick: ((NetworkPhoto) -> Unit)
    private var photoItemList = ArrayList<NetworkPhoto>()

    fun setPhotos(photosList: ArrayList<NetworkPhoto>) {
        val previousSize = itemCount
        val newSize = photosList.size

        if (newSize < previousSize) {
            notifyItemRangeRemoved(newSize, previousSize - newSize)
        }

        for (i in 0 until min(previousSize, newSize)) {
            if (photosList[i] != this.photoItemList[i]) {
                notifyItemChanged(i)
            }
        }

        if (newSize > previousSize) {
            notifyItemRangeInserted(previousSize, newSize - previousSize)
        }

        this.photoItemList = photosList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        return PhotoItemViewHolder(
            ImageItemBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false
                )
        )
    }

    override fun getItemCount(): Int = photoItemList.size

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(photoItemList[position].src?.small)
            .preload()

        Glide.with(holder.itemView)
            .asBitmap()
            .load(photoItemList[position].src?.small)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.picture)
            .into(holder.binding.photoImageView)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(photoItemList[position])
        }
    }
}