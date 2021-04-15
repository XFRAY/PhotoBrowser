package com.example.photobrowser.view.photo_browser

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.databinding.ItemPhotoFooterBinding

class PhotoBrowserFooterViewHolder(
    private val binding: ItemPhotoFooterBinding,
    private val onRetryClickListener: View.OnClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(loadingState: LoadingState) {
        binding.vwSomethingWentWrong.setOnRetryClickListener(onRetryClickListener)
        when (loadingState) {
            LoadingState.ERROR -> {
                binding.vwSomethingWentWrong.isVisible = true
                binding.progressBar.isGone = true
            }
            LoadingState.LOADING -> {
                binding.vwSomethingWentWrong.isGone = true
                binding.progressBar.isVisible = true
            }
        }
    }
}