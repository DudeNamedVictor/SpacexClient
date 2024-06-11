package com.example.spacexclient_spirin.presentation.rockets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class ParametersItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            top = spaceSize
            left = spaceSize
            right = spaceSize
            bottom = spaceSize
        }
    }

}