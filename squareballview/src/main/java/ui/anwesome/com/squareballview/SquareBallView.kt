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

    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }
        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class SquareBall(var i : Int, private val state : State = State()) {
        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val size : Float = (w/3 * state.scales[0])
            paint.strokeWidth = Math.min(w,h)/60
            paint.strokeCap = Paint.Cap.ROUND
            val sizeY : Float = (w/3 * state.scales[2])
            var sx : Float = 0f
            var sy : Float = 0f
            for(i in 0..1) {
                sx -= state.scales[3 + i*2]
                sy += state.scales[4 + i *2]
            }
            canvas.save()
            canvas.translate(w/2, h/2)
            for (i in 0..1) {
                canvas.save()
                canvas.rotate(90f * i * state.scales[1])
                for (j in 0..1) {
                    canvas.drawLine(-size, sizeY * (1 - 2 * j), size, sizeY * (1 - 2 * j), paint)
                }

                canvas.restore()
            }
            canvas.save()
            canvas.translate(-w/3, -w/3)
            canvas.drawCircle(2 * w/3 * sx, 2 * w/3 * sy, w/10, paint)
            canvas.restore()
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : SquareBallView) {

        private val squareBall : SquareBall = SquareBall(0)

        private val animator : Animator = Animator(view)

        fun render(canvas : Canvas, paint : Paint) {
            paint.color = Color.parseColor("#009688")
            canvas.drawColor(Color.parseColor("#212121"))
            squareBall.draw(canvas, paint)
            animator.animate {
                squareBall.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            squareBall.startUpdating {
                animator.start()
            }
        }
    }
}