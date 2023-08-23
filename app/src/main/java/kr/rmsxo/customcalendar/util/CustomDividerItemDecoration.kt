package kr.rmsxo.customcalendar.util

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.rmsxo.customcalendar.R

class CustomDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val horizontalDivider = ContextCompat.getDrawable(context, R.drawable.horizontal_divider)
    private val verticalDivider = ContextCompat.getDrawable(context, R.drawable.vertical_divider)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        // 일반 아이템에 대한 수평 선 그리기
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i) ?: continue
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + (horizontalDivider?.intrinsicHeight ?: 0)

            horizontalDivider?.setBounds(left, top, right, bottom)
            horizontalDivider?.draw(c)
        }

        // 맨 윗쪽 선 추가
        horizontalDivider?.setBounds(left, parent.paddingTop, right, parent.paddingTop + (horizontalDivider?.intrinsicHeight ?: 0))
        horizontalDivider?.draw(c)

        // 맨 아랫쪽 선 추가
        horizontalDivider?.setBounds(left, parent.height - (horizontalDivider?.intrinsicHeight ?: 0), right, parent.height)
        horizontalDivider?.draw(c)
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        // 일반 아이템에 대한 수직 선 그리기
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i) ?: continue
            val params = child.layoutParams as RecyclerView.LayoutParams

            val left = child.right + params.rightMargin
            val right = left + (verticalDivider?.intrinsicWidth ?: 0)

            verticalDivider?.setBounds(left, top, right, bottom)
            verticalDivider?.draw(c)
        }

        // 맨 왼쪽 선 추가
        verticalDivider?.setBounds(parent.paddingLeft, top, parent.paddingLeft + (verticalDivider?.intrinsicWidth ?: 0), bottom)
        verticalDivider?.draw(c)

        // 맨 오른쪽 선 추가
        verticalDivider?.setBounds(parent.width - (verticalDivider?.intrinsicWidth ?: 0), top, parent.width, bottom)
        verticalDivider?.draw(c)
    }
}
