package com.example.photobrowser.view.photo_browser

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.photobrowser.R
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.databinding.ActivityPhotoBrowserBinding
import com.example.photobrowser.view.loading_state.LoadingStateAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoBrowserActivity : AppCompatActivity() {

    private val photoBrowserViewModel: PhotoBrowserViewModel by viewModel()
    private lateinit var binding: ActivityPhotoBrowserBinding
    private lateinit var photoBrowserAdapter: PhotoBrowserAdapter
    private lateinit var loadingStateAdapter: LoadingStateAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_browser)
        setupPhotoBrowserAdapter()
        observeData()
        setupListeners()
    }

    private fun setupListeners() {
        binding.somethingWentWrong.setOnRetryClickListener {
            photoBrowserViewModel.onRetryLoad()
        }
    }

    private fun setupPhotoBrowserAdapter() {
        concatAdapter = ConcatAdapter()
        photoBrowserAdapter = PhotoBrowserAdapter()
        concatAdapter.addAdapter(photoBrowserAdapter)
        loadingStateAdapter = LoadingStateAdapter(onRetryAfterClickListener)
        val snapHelper = PagerSnapHelper()
        with(binding.photoList) {
            adapter = concatAdapter
            snapHelper.attachToRecyclerView(this)
        }
    }

    private val onRetryAfterClickListener = View.OnClickListener {
        photoBrowserViewModel.onRetryLoad()
    }

    private fun observeData() {
        photoBrowserViewModel.getPhotoViewListData().observe(this, {
            photoBrowserAdapter.submitList(it)
        })

        photoBrowserViewModel.getLoadingStateData().observe(this, {
            handleLoadingState(it)
        })

        photoBrowserViewModel.getInitialLoadingStateData().observe(this, {
            handleInitialLoadingState(it)
        })
    }

    private fun handleLoadingState(loadingState: LoadingState) {
        loadingStateAdapter.loadingState = loadingState
        if (loadingState == LoadingState.LOADING || loadingState == LoadingState.ERROR) {
            concatAdapter.addAdapter(loadingStateAdapter)
        } else {
            concatAdapter.removeAdapter(loadingStateAdapter)
        }
    }


    private fun handleInitialLoadingState(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.ERROR -> {
                binding.progressBar.isGone = true
                binding.somethingWentWrong.isVisible = true
            }

            LoadingState.LOADING -> {
                binding.somethingWentWrong.isGone = true
                binding.progressBar.isVisible = true
            }

            LoadingState.SUCCESS -> {
                binding.progressBar.isGone = true
            }
        }
    }
}