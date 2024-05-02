package com.example.pexelsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pexelsapp.databinding.ImageBookmarkItemBinding
import com.example.pexelsapp.domain.models.Photo
import javax.inject.Inject

class BookmarkItemAdapter @Inject constructor() : RecyclerView.Adapter<BookmarkItemAdapter.BookmarkItemViewHolder>() {
    lateinit var onItemClick:((Photo) -> Unit)
    private var photoItemList = ArrayList<Photo>()

    fun setPhotos(photosList: ArrayList<Photo>) {
        this.photoItemList = photosList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkItemViewHolder {
        return BookmarkItemViewHolder(
            ImageBookmarkItemBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false
                )
        )
    }

    override fun getItemCount(): Int {
        return photoItemList.size
    }

    override fun onBindViewHolder(holder: BookmarkItemViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .asBitmap()
            .load(photoItemList[position].src?.small)
            .into(holder.binding.photoImageView)

        holder.binding.nameSurname.text = photoItemList[position].photographer.toString()

        holder.itemView.setOnClickListener {
            onItemClick.invoke(photoItemList[position])
        }
    }

    class BookmarkItemViewHolder(val binding: ImageBookmarkItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}