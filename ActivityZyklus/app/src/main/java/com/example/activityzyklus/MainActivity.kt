package com.example.activityzyklus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.activityzyklus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var B: ActivityMainBinding
    
    private var createZaehler = 0
    private var pauseZaehler = 0
    private var resumeZaehler = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        B = ActivityMainBinding.inflate(layoutInflater)
        setContentView(B.root)

        createZaehler++
        B.tvCreate.text = "Create: $createZaehler"

    }

    override fun onPause() {

        super.onPause()

        pauseZaehler++
        B.tvPause.text = "Pause: $pauseZaehler"

    }

    override fun onResume() {

        super.onResume()

        resumeZaehler++
        B.tvResume.text = "Resume: $resumeZaehler"

    }
}
