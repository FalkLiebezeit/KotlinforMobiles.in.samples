package com.liebezeit.fakultaet

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var counter = 0

    private var fac = 0


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val  text: TextView = findViewById<TextView>(R.id.counterValue)

        val plusButton: Button = findViewById<Button>(R.id.plusBtn)
        val minusButton: Button = findViewById<Button>(R.id.minusBtn)

        val resultButton: Button = findViewById<Button>(R.id.resultBtn)
        val result: TextView = findViewById<TextView>(R.id.resultValue)



        plusButton.setOnClickListener {
            counter++
            text.text = counter.toString()
        }

        minusButton.setOnClickListener {
            counter--
            text.text = counter.toString()
        }

        resultButton.setOnClickListener {

            result.text = factorial(counter.toLong()).toString()
        }



    }

    private fun factorial(num: Long): Long {

        if (num >= 1)
            return num * factorial(num - 1)
        else
            return 1

    }

}