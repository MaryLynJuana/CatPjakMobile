package ua.kpi.comsys.ip8313.ui.pictures

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PicturesLayoutManager: RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    private fun updateSpecWithExtra(spec: Int, startInset: Int, endInset: Int): Int {
        if (startInset == 0 && endInset == 0) {
            return spec
        }
        val mode: Int = View.MeasureSpec.getMode(spec)
        return if (mode == View.MeasureSpec.AT_MOST || mode == View.MeasureSpec.EXACTLY) {
            View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.getSize(spec) - startInset - endInset, mode
            )
        } else spec
    }

    private fun measureChildWithDecorationsAndMargin(
        child: View,
        widthSpec: Int,
        heightSpec: Int
    ) {
        var widthSpec = widthSpec
        var heightSpec = heightSpec
        val decorRect = Rect()
        calculateItemDecorationsForChild(child, decorRect)
        val lp = child.layoutParams as RecyclerView.LayoutParams
        widthSpec = updateSpecWithExtra(
            widthSpec, lp.leftMargin + decorRect.left,
            lp.rightMargin + decorRect.right
        )
        heightSpec = updateSpecWithExtra(
            heightSpec, lp.topMargin + decorRect.top,
            lp.bottomMargin + decorRect.bottom
        )
        child.measure(widthSpec, heightSpec)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        detachAndScrapAttachedViews(recycler)
        val one = width / 5
        var currentPos = 0
        var top = 0
        var bottom = 0
        var left = 0
        var right = 0
        while (currentPos < itemCount) {
            val view = recycler.getViewForPosition(currentPos)
            addView(view)
            when (currentPos % 6) {
                0 -> {
                    measureChildWithMargins(view, 0, 0)
                    var widthSpec = View.MeasureSpec.makeMeasureSpec(3 * one, View.MeasureSpec.EXACTLY)
                    var heightSpec = View.MeasureSpec.makeMeasureSpec(3 * one, View.MeasureSpec.EXACTLY)
                    measureChildWithDecorationsAndMargin(view, widthSpec, heightSpec)
                }
                1, 2 -> {
                    measureChildWithMargins(view, 0, 0)
                    var widthSpec = View.MeasureSpec.makeMeasureSpec(2 * one, View.MeasureSpec.EXACTLY)
                    var heightSpec = View.MeasureSpec.makeMeasureSpec(2 * one, View.MeasureSpec.EXACTLY)
                    measureChildWithDecorationsAndMargin(view, widthSpec, heightSpec)
                }
                3, 4, 5 -> {
                    measureChildWithMargins(view, 0, 0)
                    var widthSpec = View.MeasureSpec.makeMeasureSpec(one, View.MeasureSpec.EXACTLY)
                    var heightSpec = View.MeasureSpec.makeMeasureSpec(one, View.MeasureSpec.EXACTLY)
                    measureChildWithDecorationsAndMargin(view, widthSpec, heightSpec)
                }
            }

            when (currentPos % 6) {
                0 -> {
                    top = getDecoratedBottom(view)
                    bottom = top + 3 * one
                    right = 3 * one
                }
                1 -> {
                    bottom = top + 2 * one
                    left = getDecoratedRight(view)
                    right = width
                }
                2 -> {
                    top = getDecoratedBottom(view)
                    bottom = top + 2 * one
                }
                3 -> {
                    top = getDecoratedBottom(view)
                    bottom = top + one
                    left = 0
                    right = left + one
                }
                4 -> {
                    left = getDecoratedRight(view)
                    right = left + one
                }
                5 -> {
                    left = getDecoratedRight(view)
                    right = left + one
                }
            }
            layoutDecorated(view, left, top, right, bottom)
            currentPos++
        }
    }
}