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


    constructor(ctx: Context?) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val SQUARE_SIZE = 25

    private val topLeft : Pocket
    private val topRight : Pocket
    private val bottomLeft : Pocket
    private val bottomRight : Pocket

    private val paint: Paint = Paint()

    val cardGenerator = CardGenerator(listOf(CardColor.RED), listOf(CardPip.CIRCLE), 1,1)
    var currentCard = Card(CardColor.RED, CardPip.CIRCLE, 1)

    var rectList = ArrayList<Rect>()

    private var touchOrigin: MotionEvent? = null
    private var touchCurrent : MotionEvent? = null
    private var originRect: Rect? = null
    private var currentRect : Rect? = null

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.0f
        paint.alpha = 255

        // todo dynamic sizing
        topLeft = Pocket(0, 0, 312, 312)
        topRight = Pocket(712,0,1024,312)
        bottomLeft = Pocket(0, 456, 312, 768)
        bottomRight = Pocket(712, 456, 1024, 768)

    }

    override fun performClick(): Boolean {
        println(">>> performClick...")
        return super.performClick()

    }

    private fun setOrigin(origin : MotionEvent) {

        touchOrigin = origin

        val originX = touchOrigin!!.rawX.toInt()
        val originY = touchOrigin!!.rawY.toInt()

        println(">>> ORIGIN SET")
        println(">>> $originX $originY")

        originRect = Rect()
        originRect!!.set((touchOrigin!!.rawX.toInt() - SQUARE_SIZE),
                (touchOrigin!!.rawY.toInt() - SQUARE_SIZE),
                (touchOrigin!!.rawX.toInt() + SQUARE_SIZE),
                (touchOrigin!!.rawY.toInt() + SQUARE_SIZE))
    }

    private fun setCurrent(current : MotionEvent) {
        touchCurrent = current
        println(">>> CURRENT SET")
        println(">>> ${touchCurrent!!.x.toInt()} ${touchCurrent!!.y.toInt()}")

        currentRect = Rect()
        currentRect!!.set(touchCurrent!!.rawX.toInt() - SQUARE_SIZE,
                touchCurrent!!.rawY.toInt() - SQUARE_SIZE,
                touchCurrent!!.rawX.toInt() + SQUARE_SIZE,
                touchCurrent!!.rawY.toInt() + SQUARE_SIZE)

    }

    private fun sendCardToPocket(card : Card, pocket : Pocket) {
        pocket.take(card)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        super.performClick()

        if (event == null)
            return false

        return when (event.action) {

            MotionEvent.ACTION_DOWN -> {

                if (touchOrigin == null) {
                    setOrigin(event)
                }
                touchCurrent = null

                true
            }

            MotionEvent.ACTION_MOVE -> {

                setCurrent(event)

//                // fixme this is really inefficient
//                if (topRight.getRect().contains(event.rawX.toInt(), event.rawY.toInt())) {
//                    topRight.setSelected(true)
//                } else {
//                    topRight.setSelected(false)
//                }
//
//                if (topLeft.getRect().contains(event.rawX.toInt(), event.rawY.toInt())) {
//                    topLeft.setSelected(true)
//                } else {
//                    topLeft.setSelected(false)
//                }
//
//                if (bottomLeft.getRect().contains(event.rawX.toInt(), event.rawY.toInt())) {
//                    bottomLeft.setSelected(true)
//                } else {
//                    bottomLeft.setSelected(false)
//                }
//
//                if (bottomRight.getRect().contains(event.rawX.toInt(), event.rawY.toInt())) {
//                    bottomRight.setSelected(true)
//                } else {
//                    bottomRight.setSelected(false)
//                }

                false
            }

            MotionEvent.ACTION_UP -> {

                if (touchCurrent == null)
                    return false

                setCurrent(event)

                // detect a flick
                val originX = touchOrigin!!.rawX.toInt()
                val originY = touchOrigin!!.rawY.toInt()
                val currentX = touchCurrent!!.rawX.toInt()
                val currentY = touchCurrent!!.rawY.toInt()

                println("$originX $originY / $currentX $currentY")

                if (currentX >= originX) {

                    if (currentY >= originY) {
                        println(">>> card to TOP RIGHT")
                        sendCardToPocket(currentCard, topRight)
                    } else {
                        println(">>> card to TOP LEFT")
                        sendCardToPocket(currentCard, topLeft)
                    }

                    currentCard = cardGenerator.getNextCard()
                }

//                topRight.setSelected(false)
//                topLeft.setSelected(false)
//                bottomLeft.setSelected(false)
//                bottomRight.setSelected(false)

                touchOrigin = null
                originRect = null
                touchCurrent = null
                currentRect = null

                false
            }

            else -> false

        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        rectList.clear()

        if (originRect != null) {
            rectList.add(originRect!!)
        }

        if (currentRect != null) {
            rectList.add(currentRect!!)
        }


        // clear canvas
        canvas?.drawColor(Color.WHITE)
        // draw pockets
        canvas?.drawRect(topLeft.getRect(), topLeft.getColor())
        canvas?.drawRect(topRight.getRect(), topRight.getColor())
        canvas?.drawRect(bottomLeft.getRect(), bottomLeft.getColor())
        canvas?.drawRect(bottomRight.getRect(), bottomRight.getColor())

        rectList.forEach { canvas?.drawRect(it, paint) }

    }




}