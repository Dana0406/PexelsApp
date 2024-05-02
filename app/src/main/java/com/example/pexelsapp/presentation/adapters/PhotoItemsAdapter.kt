package com.example.pexelsapp.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pexelsapp.R
import com.example.pexelsapp.databinding.ImageItemBinding
import com.example.pexelsapp.domain.models.Photo
import javax.inject.Inject


class PhotoItemsAdapter @Inject constructor() : RecyclerView.Adapter<PhotoItemsAdapter.PhotoItemViewHolder>() {
    lateinit var onItemClick:((Photo) -> Unit)
    private var photoItemList = ArrayList<Photo>()

    fun setPhotos(photosList: ArrayList<Photo>) {
        this.photoItemList = photosList
        notifyDataSetChanged()
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

    override fun getItemCount(): Int {
        return photoItemList.size
    }

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

    class PhotoItemViewHolder(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}