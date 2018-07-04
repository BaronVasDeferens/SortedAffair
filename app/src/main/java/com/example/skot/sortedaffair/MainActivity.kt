package com.example.skot.sortedaffair

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var flicker: FlickerTwo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flicker = FlickerTwo(this)
        flicker.background = resources.getDrawable(R.color.white)
        flicker.setOnTouchListener(this)
        val mainLayout = findViewById<LinearLayout>(R.id.mainLayout)
        mainLayout.addView(flicker)

    }


    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        view?.performClick()
        val retVal =  view?.onTouchEvent(motionEvent) ?: false
        view?.invalidate()
        return retVal
    }
}
