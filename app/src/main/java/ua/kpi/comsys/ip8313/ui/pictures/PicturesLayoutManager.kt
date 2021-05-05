package ua.kpi.comsys.ip8313.ui.pictures

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

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
        val one = width / 3
        var currentPos = 0
        var top = 0
        var bottom = one
        var left = 0
        var right = one
        while (currentPos < itemCount) {
            val view = recycler.getViewForPosition(currentPos)
            addView(view)

            var widthSpec = View.MeasureSpec.makeMeasureSpec(one, View.MeasureSpec.EXACTLY)
            var heightSpec = View.MeasureSpec.makeMeasureSpec(one, View.MeasureSpec.EXACTLY)
            if (currentPos % 9 == 4) {
                    widthSpec = View.MeasureSpec.makeMeasureSpec(2 * one, View.MeasureSpec.EXACTLY)
                    heightSpec = View.MeasureSpec.makeMeasureSpec(2 * one, View.MeasureSpec.EXACTLY)
            }

            measureChildWithDecorationsAndMargin(view, widthSpec, heightSpec)

            if (currentPos == 0) {
                layoutDecorated(view, left, top, right, bottom)
                currentPos++
                continue
            }

            when (currentPos % 9) {
                0 -> {
                    top += one
                    bottom = top + one
                    left = 0
                    right = one
                }
                1, 2 -> {
                    left += one
                    right = left + one
                }
                3, 5, 6 -> {
                    top += one
                    bottom = top + one
                    left = 0
                    right = left + one
                }
                4 -> {
                    left += one
                    right = left + 2 * one
                    bottom += one
                }
                7, 8 -> {
                    left += one
                    right = left + one
                }
            }
            layoutDecorated(view, left, top, right, bottom)
            currentPos++
        }
    }

    override fun canScrollVertically(): Boolean = true

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val delta = scrollVerticallyInternal(dy)
        offsetChildrenVertical(-delta)
        return delta
    }

    private fun scrollVerticallyInternal(dy: Int): Int {
        if (childCount == 0) {
            return 0
        }

        val topView = getChildAt(0)
        val bottomView = getChildAt(childCount - 1)

        val viewSpan = getDecoratedBottom(bottomView!!) - getDecoratedTop(topView!!)
        if (viewSpan <= height) return 0

        var delta = 0
        if (dy < 0) {
            val firstView = getChildAt(0)
            val firstViewPosition = getPosition(firstView!!)
            delta = if (firstViewPosition > 0) dy else max(getDecoratedTop(firstView), dy)
        } else if (dy > 0) {
            val lastView = getChildAt(childCount - 1)
            val lastViewPosition = getPosition(lastView!!)
            delta = if (lastViewPosition < itemCount - 1) dy else min(getDecoratedBottom(lastView) - height, dy)
        }
        return delta
    }
}