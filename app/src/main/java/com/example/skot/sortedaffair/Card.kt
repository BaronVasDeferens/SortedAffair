package com.example.skot.sortedaffair

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

enum class CardColor (val colorValue : Int){
    RED (Color.argb(255,255,102,102)),
    BLUE (Color.argb(255,102,204,255)),
    GREEN (Color.argb(255,102,255,102)),
    MAGENTA (Color.argb(255,204,153,255)),
    ORANGE (Color.argb(255, 255,153,0))
}

enum class CardPip {
    CIRCLE, SQUARE, TRIANGLE
}

class Card (val color : CardColor = CardColor.RED, val pipType : CardPip = CardPip.CIRCLE, val pipNumber : Int = 1) {

    private var paint: Paint = Paint()
    private val screenWidth = 1024
    private val screenHeight = 768
    private val cardWidth = 250
    private val cardHeight = 400

    private val defaultRect = Rect()

    init {
        paint.color = color.colorValue
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.alpha = 255

        defaultRect.set(
                screenWidth/2 - cardWidth / 2,
                screenHeight/2 - cardHeight/2,
                screenWidth/2 - cardWidth / 2 + cardWidth,
                screenHeight/2 + - cardHeight/2 + cardHeight)
    }

    fun getCardColor(): Paint {
        return paint
    }

    // original specs 0,0,250,400
    // 387 = (1024/2) - (250/2)
    // 184 + (768/2) - (400/2)
    fun getRect() : Rect {
        return defaultRect
    }

}