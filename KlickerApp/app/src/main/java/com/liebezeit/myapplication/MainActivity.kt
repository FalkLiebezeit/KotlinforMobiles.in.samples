package com.liebezeit.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView



class MainActivity : AppCompatActivity() {

    private var counter = 0


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val  text: TextView = findViewById<TextView>(R.id.counterValue)

        val plusButton: Button = findViewById<Button>(R.id.plusBtn)
        val minusButton: Button = findViewById<Button>(R.id.minusBtn)




        plusButton.setOnClickListener {
            counter++
            text.text = counter.toString()
        }

        minusButton.setOnClickListener {
            counter--
            text.text = counter.toString()
        }


    }

}