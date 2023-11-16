package com.hamon.albochallengenormal.components

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.hamon.albochallengenormal.databinding.CustomViewCountBinding

class CountViewCustom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: CustomViewCountBinding =
        CustomViewCountBinding.inflate(LayoutInflater.from(context), this, true)

    private val listPainView = mutableListOf(
        binding.countOne,
        binding.countTwo,
        binding.countThree,
        binding.countFour,
        binding.countFive
    )

    private var actualCount: Int = 0
    private var maxCount: Int = 0

    fun setupCounter(actualCount: Int, maxCount: Int) {
        this.actualCount = actualCount
        this.maxCount = maxCount
        binding.tvLimitCount.text = "${actualCount}/${maxCount} tarjetazos"
        checkActiveSegments()
    }

    fun updateSegments(updateValue: Int){
        this.actualCount = updateValue
        checkActiveSegments()
    }

    private fun checkActiveSegments() {
        val result = (actualCount * 100) / maxCount

        when{
            result > 0 && result < 20 -> {
                listPainView[0].active()
                listPainView[1].disable()
                listPainView[2].disable()
                listPainView[3].disable()
                listPainView[4].disable()
            }
            result >= 20 && result < 40 -> {
                listPainView[0].active()
                listPainView[1].active()
                listPainView[2].disable()
                listPainView[3].disable()
                listPainView[4].disable()
            }
            result >= 40 && result < 60 -> {
                listPainView[0].active()
                listPainView[1].active()
                listPainView[2].active()
                listPainView[3].disable()
                listPainView[4].disable()
            }
            result >= 60 && result < 80 -> {
                listPainView[0].active()
                listPainView[1].active()
                listPainView[2].active()
                listPainView[3].active()
                listPainView[4].disable()
            }
            result >= 80 -> {
                listPainView[0].active()
                listPainView[1].active()
                listPainView[2].active()
                listPainView[3].active()
                listPainView[4].active()
            }
            else -> {
                listPainView[0].disable()
                listPainView[1].disable()
                listPainView[2].disable()
                listPainView[3].disable()
                listPainView[4].disable()
            }

        }
    }

    private fun View.active() {
        this.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, android.R.color.holo_blue_bright))
    }

    private fun View.disable() {
        this.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, android.R.color.holo_red_dark))
    }


}