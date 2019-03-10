package com.example.skot.sortedaffair

import java.util.*

class CardGenerator (val initialColors : List<CardColor> = listOf(CardColor.RED),
                     val initialPipTypes : List<CardPip> = listOf(CardPip.CIRCLE),
                     val minPips : Int = 1, val maxPips : Int = 1) {

    private val random = Random()

    fun getNextCard() : Card {

        val color = CardColor.values()[CardColor.values().size -1]
        val pipType = CardPip.values()[CardPip.values().size -1]
        val pips = minPips + random.nextInt(maxPips)

        return Card(color, pipType, pips)
    }

    fun getRandomlyColoredCard() : Card {
        return Card(
                CardColor.values()[random.nextInt(CardColor.values().size)],
                CardPip.CIRCLE,
                1)
    }

}