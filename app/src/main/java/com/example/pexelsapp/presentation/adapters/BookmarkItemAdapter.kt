package com.example.pexelsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.databinding.ImageBookmarkItemBinding
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.presentation.adapters.viewHolders.BookmarkItemViewHolder
import javax.inject.Inject

class BookmarkItemAdapter() :
    RecyclerView.Adapter<BookmarkItemViewHolder>() {

    lateinit var onItemClick: ((DBPhoto) -> Unit)
    private var photoItemList = ArrayList<DBPhoto>()

    fun setPhotos(photosList: ArrayList<DBPhoto>) {
        val previousSize = photoItemList.size
        this.photoItemList = photosList
        val newSize = photosList.size
        val itemCount = if (previousSize < newSize) previousSize else newSize

        if (itemCount > 0) {
            notifyItemRangeChanged(0, itemCount)
        } else if (newSize > previousSize) {
            notifyItemRangeInserted(previousSize, newSize - previousSize)
        } else if (newSize < previousSize) {
            notifyItemRangeRemoved(newSize, previousSize - newSize)
        }
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

    override fun getItemCount(): Int = photoItemList.size

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
}
