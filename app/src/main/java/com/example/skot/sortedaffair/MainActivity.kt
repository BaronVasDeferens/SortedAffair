package com.example.skot.sortedaffair

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

class MainActivity : AppCompatActivity(), View.OnTouchListener {

    var flicker: FlickerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flicker = FlickerView(this)
        flicker!!.setOnTouchListener(this)
        val mainLayout = findViewById<LinearLayout>(R.id.mainLayout)
        mainLayout.addView(flicker)

    }


    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {

        flicker!!.setMotionEvent(motionEvent!!)
        flicker!!.invalidate()

        return when (motionEvent?.action) {

            MotionEvent.ACTION_UP -> {
                false
            }
            MotionEvent.ACTION_DOWN -> {
                true
            }
            else -> {
                false
            }
        }

    }
}
