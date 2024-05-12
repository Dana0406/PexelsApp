package com.example.pexelsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pexelsapp.R
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.databinding.ImageItemBinding
import com.example.pexelsapp.presentation.adapters.viewHolders.PhotoItemViewHolder

class PhotoItemsAdapter() :
    PagingDataAdapter<NetworkPhoto, PhotoItemViewHolder>(NetworkPhotoDiffCallback()) {

    lateinit var onItemClick: ((NetworkPhoto) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        return PhotoItemViewHolder(
            ImageItemBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        val photoItem = getItem(position)
        photoItem?.let { photo ->
            Glide.with(holder.itemView.context)
                .load(photo.src?.small)
                .preload()

            Glide.with(holder.itemView)
                .asBitmap()
                .load(photo.src?.small)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.picture)
                .into(holder.binding.photoImageView)

            holder.itemView.setOnClickListener {
                onItemClick.invoke(photo)
            }
        }
    }
}

class NetworkPhotoDiffCallback : DiffUtil.ItemCallback<NetworkPhoto>() {
    override fun areItemsTheSame(oldItem: NetworkPhoto, newItem: NetworkPhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NetworkPhoto, newItem: NetworkPhoto): Boolean {
        return oldItem == newItem
    }
}

