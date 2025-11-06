package com.example.auswahleinfach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.auswahleinfach.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var B: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        B = ActivityMainBinding.inflate(layoutInflater)

        setContentView(B.root)


        
        B.cbSchalter.setOnClickListener {
            B.tvCheckBox.text =
                    if(B.cbSchalter.isChecked) "An" else "Aus"
        }

        B.buCheckBoxEin.setOnClickListener {
            B.cbSchalter.isChecked = true
            B.tvCheckBox.text = "An"
        }

        B.buCheckBoxUm.setOnClickListener {
            B.cbSchalter.toggle()
            B.tvCheckBox.text =
                    if(B.cbSchalter.isChecked) "An" else "Aus"
        }

        B.tbSchalter.textOn = "An"
        B.tbSchalter.textOff = "Aus"

        B.tbSchalter.setOnClickListener {
            B.tvToggle.text =
                    if(B.tbSchalter.isChecked) "An" else "Aus"
        }

        B.buToggleEin.setOnClickListener {
            B.tbSchalter.isChecked = true
            B.tvToggle.text = "An"
        }

        B.buToggleUm.setOnClickListener  {
            B.tbSchalter.toggle()
            B.tvToggle.text =
                    if(B.tbSchalter.isChecked) "An" else "Aus"
        }

        B.swSchalter.setOnClickListener {
            B.tvSwitch.text =
                    if(B.swSchalter.isChecked) "An" else "Aus"
        }

        B.buSwitchEin.setOnClickListener {
            B.swSchalter.isChecked = true
            B.tvSwitch.text = "An"
        }

        B.buSwitchUm.setOnClickListener {
            B.swSchalter.toggle()
            B.tvSwitch.text =
                    if(B.swSchalter.isChecked) "An" else "Aus"
        }
    }
}
