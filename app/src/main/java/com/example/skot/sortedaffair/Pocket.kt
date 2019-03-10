package com.example.skot.sortedaffair

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class Pocket {

    val cards = ArrayList<Card>()

    private var isSelected = false
    private var myRect = Rect()
    private var selectedColor = Paint()
    private val regularColor = Paint()

    constructor(left: Int, top: Int, right: Int, bottom: Int) {
        myRect.set(left, top, right, bottom)

        selectedColor.color = Color.RED
        selectedColor.style = Paint.Style.FILL_AND_STROKE
        selectedColor.alpha = 255

        regularColor.color = Color.WHITE
        regularColor.style = Paint.Style.FILL_AND_STROKE
        regularColor.alpha = 255

    }

    fun setSelected(select: Boolean, selectedColor : Paint) {
        isSelected = select
        this.selectedColor = selectedColor
    }

    fun getRect(): Rect {
        return myRect
    }

    fun getColor() : Paint {
        return if (isSelected) selectedColor
        else regularColor
    }

    fun take (card : Card) {
        cards.add(card)
    }

}