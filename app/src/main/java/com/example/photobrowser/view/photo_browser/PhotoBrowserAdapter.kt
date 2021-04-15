package com.example.photobrowser.view.photo_browser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.data.model.ui.PhotoUI
import com.example.photobrowser.databinding.ItemPhotoBinding
import com.example.photobrowser.databinding.ItemPhotoFooterBinding

class PhotoBrowserAdapter(private val onRetryClickListener: View.OnClickListener) : PagedListAdapter<PhotoUI, RecyclerView.ViewHolder>(photoDiffCallback) {

    companion object {
        private const val PHOTO_VIEW_TYPE = 0
        private const val FOOTER_VIEW_TYPE = 1

        private val photoDiffCallback = object : DiffUtil.ItemCallback<PhotoUI>() {
            override fun areItemsTheSame(oldItem: PhotoUI, newItem: PhotoUI): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PhotoUI, newItem: PhotoUI): Boolean =
                oldItem.photoUrl == newItem.photoUrl
        }
    }

    private var loadingState = LoadingState.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FOOTER_VIEW_TYPE) {
            val binding = ItemPhotoFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            PhotoBrowserFooterViewHolder(binding, onRetryClickListener)
        } else {
            val binding =
                ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PhotoBrowserViewHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == PHOTO_VIEW_TYPE) {
            getItem(position)?.let { (holder as PhotoBrowserViewHolder).bindItem(it) }
        } else {
            (holder as PhotoBrowserFooterViewHolder).bindItem(loadingState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) PHOTO_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) +1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (loadingState == LoadingState.LOADING || loadingState == LoadingState.ERROR)
    }

    fun setLoadingState(loadingState: LoadingState) {
        this.loadingState = loadingState
        notifyItemChanged(super.getItemCount())
    }
}