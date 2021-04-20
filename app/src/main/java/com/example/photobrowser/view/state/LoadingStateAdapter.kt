package com.example.photobrowser.view.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.databinding.ItemLoadingStateBinding

class LoadingStateAdapter(
    private val onRetryClickListener: View.OnClickListener
) : RecyclerView.Adapter<LoadingStateViewHolder>() {

    var loadingState = LoadingState.LOADING
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoadingStateViewHolder {
        val binding = ItemLoadingStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadingStateViewHolder(binding, onRetryClickListener)
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, position: Int) {
        holder.bindItem(loadingState)
    }

    override fun getItemCount() = 1
}