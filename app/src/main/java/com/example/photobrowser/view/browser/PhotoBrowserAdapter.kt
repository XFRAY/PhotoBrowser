package com.example.photobrowser.view.browser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.photobrowser.data.model.ui.PhotoUI
import com.example.photobrowser.databinding.ItemPhotoBinding

class PhotoBrowserAdapter : PagedListAdapter<PhotoUI, PhotoBrowserViewHolder>(photoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoBrowserViewHolder {
        val binding =
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoBrowserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoBrowserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindItem(it)
        }
    }

    companion object {
        private val photoDiffCallback = object : DiffUtil.ItemCallback<PhotoUI>() {
            override fun areItemsTheSame(oldItem: PhotoUI, newItem: PhotoUI): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PhotoUI, newItem: PhotoUI): Boolean =
                oldItem.photoUrl == newItem.photoUrl
        }
    }
}