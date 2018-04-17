package ui.anwesome.com.squareballview

/**
 * Created by anweshmishra on 17/04/18.
 */

import android.content.Context
import android.view.View
import android.view.MotionEvent
import android.graphics.*

class SquareBallView(ctx : Context) : View(ctx) {
    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class State (var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0){
        val scales : Array<Float> = arrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if (Math.abs(scales[j] - prevScale) > 1) {
                prevScale = scales[j] + dir
                j += dir.toInt()
                if (j == scales.size || j == -1) {
                    j -= dir.toInt()
                    dir = 0f
                    scales[j] = prevScale
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb :  () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }
}