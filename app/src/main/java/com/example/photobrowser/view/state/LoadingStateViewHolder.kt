package com.example.photobrowser.view.state

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.databinding.ItemLoadingStateBinding

class LoadingStateViewHolder(
    private val binding: ItemLoadingStateBinding,
    private val onRetryClickListener: View.OnClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(loadingState: LoadingState) {
        binding.somethingWentWrong.setOnRetryClickListener(onRetryClickListener)
        when (loadingState) {
            LoadingState.ERROR -> {
                binding.somethingWentWrong.isVisible = true
                binding.progressBar.isGone = true
            }
            LoadingState.LOADING -> {
                binding.somethingWentWrong.isGone = true
                binding.progressBar.isVisible = true
            }
        }
    }
}