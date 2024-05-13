package com.example.pexelsapp.presentation.adapters.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.pexelsapp.data.models.NetworkPhoto
class NetworkPhotoDiffCallback : DiffUtil.ItemCallback<NetworkPhoto>() {
    
    override fun areItemsTheSame(oldItem: NetworkPhoto, newItem: NetworkPhoto): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: NetworkPhoto, newItem: NetworkPhoto): Boolean {
        return oldItem == newItem
    }
}