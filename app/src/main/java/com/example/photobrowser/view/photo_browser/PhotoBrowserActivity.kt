package com.example.photobrowser.view.photo_browser

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.photobrowser.R
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.databinding.ActivityPhotoBrowserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoBrowserActivity : AppCompatActivity() {

    private val photoBrowserViewModel: PhotoBrowserViewModel by viewModel()
    private lateinit var binding: ActivityPhotoBrowserBinding
    private lateinit var photoBrowserAdapter: PhotoBrowserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_browser)
        setupPhotoBrowserAdapter()
        observeData()
        setupListeners()
    }

    private fun setupListeners() {
        binding.vwSomethingWentWrong.setOnRetryClickListener {
            photoBrowserViewModel.onClickRetryInitialLoad()
        }
    }

    private fun setupPhotoBrowserAdapter() {
        photoBrowserAdapter = PhotoBrowserAdapter(onRetryAfterClickListener)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        with(binding.rvPhotoBrowser) {
            this.layoutManager = layoutManager
            adapter = photoBrowserAdapter
            snapHelper.attachToRecyclerView(this)
        }
    }

    private val onRetryAfterClickListener = View.OnClickListener {
        photoBrowserViewModel.onClickRetryAfterLoad()
    }

    private fun observeData() {
        photoBrowserViewModel.getPhotoViewListData().observe(this, {
            photoBrowserAdapter.submitList(it)
        })

        photoBrowserViewModel.getLoadingStateData().observe(this, {
            photoBrowserAdapter.setLoadingState(it)
        })

        photoBrowserViewModel.getInitialLoadingStateData().observe(this, {
            handleInitialLoadingState(it)
        })
    }

    private fun handleInitialLoadingState(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.ERROR -> {
                binding.progressBar.isGone = true
                binding.vwSomethingWentWrong.isVisible = true
            }

            LoadingState.LOADING -> {
                binding.vwSomethingWentWrong.isGone = true
                binding.progressBar.isVisible = true
            }

            LoadingState.DONE -> {
                binding.progressBar.isGone = true
            }
        }
    }
}