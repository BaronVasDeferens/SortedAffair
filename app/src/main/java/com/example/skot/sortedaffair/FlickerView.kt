package com.example.skot.sortedaffair

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class FlickerView : View {

    val SQUARE_SIZE = 25

    constructor(ctx: Context?) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val paint: Paint = Paint()
    var ptrCoords = MotionEvent.PointerCoords()
    var rectList = ArrayList<Rect>()

    private var touchOrigin: MotionEvent? = null
    private var originRect: Rect? = null
    private var touchCurrent: MotionEvent? = null

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5.0f
        paint.alpha = 255
    }

    fun setMotionEvent(event: MotionEvent) {

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {

                if (touchOrigin == null) {
                    println("eventType: DOWN -- SET ORIGIN")
                    touchOrigin = event
                    originRect = Rect()
                    originRect!!.set((touchOrigin!!.rawX.toInt() - SQUARE_SIZE),
                            (touchOrigin!!.rawY.toInt() - SQUARE_SIZE),
                            (touchOrigin!!.rawX.toInt() + SQUARE_SIZE),
                            (touchOrigin!!.rawY.toInt() + SQUARE_SIZE))

                }
            }

            MotionEvent.ACTION_MOVE -> {
                println("eventType: MOVE")
                touchCurrent = event
            }

            MotionEvent.ACTION_UP -> {
                println("eventType: UP")
            }
        }


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        println(">>> onDraw")

        rectList.clear()

        if (originRect != null) {
            rectList.add(originRect!!)
        }

        if (touchCurrent != null) {
            ptrCoords = MotionEvent.PointerCoords()
            touchCurrent!!.getPointerCoords(0, ptrCoords)
            val r2 = Rect()
            r2.set((ptrCoords.x - SQUARE_SIZE).toInt(),
                    (ptrCoords.y - SQUARE_SIZE).toInt(),
                    (ptrCoords.x + SQUARE_SIZE).toInt(),
                    (ptrCoords.y + SQUARE_SIZE).toInt())
            rectList.add(r2)
        }

        canvas?.drawColor(Color.WHITE)
        rectList.forEach { canvas?.drawRect(it, paint) }


        if (touchCurrent?.action == MotionEvent.ACTION_UP) {
            println(">>> clearing squares")
            originRect = null
            touchOrigin = null
            touchCurrent = null
            canvas?.drawColor(Color.WHITE)
        }

    }

}