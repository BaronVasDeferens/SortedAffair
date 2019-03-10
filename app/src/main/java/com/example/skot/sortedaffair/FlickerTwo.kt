package com.example.skot.sortedaffair

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

class FlickerTwo(context: Context?) : View(context) {

    private val SWIPE_DISTANCE_THRESHOLD = 50

    private var originX: Float = 0.0f
    private var originY: Float = 0.0f
    private var currentX: Float = 0.0f
    private var currentY: Float = 0.0f

    private val topLeft: Pocket
    private val topRight: Pocket
    private val bottomLeft: Pocket
    private val bottomRight: Pocket

    private val cardGenerator = CardGenerator(listOf(CardColor.RED), listOf(CardPip.CIRCLE), 1 ,1)
    private var currentCard = cardGenerator.getRandomlyColoredCard()

    private val paint = Paint()
    private val rect = Rect()

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 1.0f
        paint.alpha = 255

        topLeft = Pocket(0, 0, 312, 312)
        topRight = Pocket(712, 0, 1024, 312)
        bottomLeft = Pocket(0, 456, 312, 768)
        bottomRight = Pocket(712, 456, 1024, 768)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                println("DOWN")
                originX = event.x
                originY = event.y
                currentX = event.x
                currentY = event.y
                true
            }

            MotionEvent.ACTION_MOVE -> {
                currentX = event.x
                currentY = event.y
                true
            }

            // The finger has come up, indicating the finish of the gesture
            MotionEvent.ACTION_UP -> {
                println("UP")

                currentX = event.x
                currentY = event.y
                println("($originX $originY) to ($currentX $currentY)")

                // Compute delta
                val deltaX = (originX-currentX).toInt()
                val deltaY = (originY-currentY).toInt()
                if (Math.abs(deltaX) <= SWIPE_DISTANCE_THRESHOLD || Math.abs(deltaY) <= SWIPE_DISTANCE_THRESHOLD) {
                    println(">>> SWIPE TOO SHORT")
                    originX = -1.0f
                    originY = -1.0f
                    currentX = -1.0f
                    currentY = -1.0f
                    return true
                }

                if (originX <= currentX) {
                    if (originY <= currentY) {
                        bottomRight.setSelected(true, currentCard.getCardColor())
                    } else {
                        topRight.setSelected(true, currentCard.getCardColor())
                    }
                } else {
                    if (originY <= currentY) {
                        bottomLeft.setSelected(true, currentCard.getCardColor())
                    } else {
                        topLeft.setSelected(true, currentCard.getCardColor())
                    }
                }

                currentCard = cardGenerator.getRandomlyColoredCard()

                originX = -1.0f
                originY = -1.0f
                currentX = -1.0f
                currentY = -1.0f

                true
            }
            else -> {
                false
            }
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {

            // Draw pockets
            canvas.drawRect(topLeft.getRect(), topLeft.getColor())
            canvas.drawRect(topRight.getRect(), topRight.getColor())
            canvas.drawRect(bottomLeft.getRect(), bottomLeft.getColor())
            canvas.drawRect(bottomRight.getRect(), bottomRight.getColor())

            // Draw the current card
            val cardRect = currentCard.getRect()

            if (originX + originY + currentX + currentY > 0) {
                cardRect.set(
                        currentX.toInt() - 125,
                        currentY.toInt() - 200,
                        currentX.toInt() - 125 + 250,
                        currentY.toInt() - 200 + 400)
            }  // else Draw the card in the center of the screen



            canvas.drawRect(cardRect, currentCard.getCardColor())
        }
    }
}