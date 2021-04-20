package com.example.photobrowser.view.browser

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.photobrowser.data.model.ui.PhotoUI
import com.example.photobrowser.databinding.ItemPhotoBinding
import com.example.photobrowser.extensions.view.dpToPx

class PhotoBrowserViewHolder(
    private val binding: ItemPhotoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(photoUI: PhotoUI) {
        val photoUrl = photoUI.photoUrl
        val context = binding.root.context
        val photoRequestOptions = RequestOptions()
            .centerCrop()
        Glide.with(context)
            .load(photoUrl)
            .placeholder(getCircularProgressDrawable(context))
            .apply(photoRequestOptions)
            .into(binding.photo)
    }

    private fun getCircularProgressDrawable(context: Context) =
        CircularProgressDrawable(context).apply {
            strokeWidth = context.dpToPx(PROGRESS_STROKE_WIDTH)
            centerRadius = context.dpToPx(PROGRESS_CENTER_RADIUS)
            start()
        }

    companion object {
        private const val PROGRESS_STROKE_WIDTH = 5F
        private const val PROGRESS_CENTER_RADIUS = 30F
    }
}