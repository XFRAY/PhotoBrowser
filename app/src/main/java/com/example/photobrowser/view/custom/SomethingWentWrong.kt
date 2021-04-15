package com.example.photobrowser.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.photobrowser.databinding.ViewSomethingWentWrongBinding

class SomethingWentWrong @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewSomethingWentWrongBinding.inflate(LayoutInflater.from(context), this, true)

    fun setOnRetryClickListener(onClickListener: OnClickListener) {
        binding.btnRetry.setOnClickListener(onClickListener)
    }

}