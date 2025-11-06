package com.example.bruchtraining

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bruchtraining.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var B: ActivityMainBinding

    private var ergz = 0
    private var ergn = 0
    private val prob = intArrayOf(2, 2, 2, 2, 3, 3, 3, 5, 5, 7)
    private val prim = intArrayOf(2, 3, 5, 7)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        B = ActivityMainBinding.inflate(layoutInflater)

        setContentView(B.root)

        var ergw = 0
        val ops = charArrayOf('+', '-', '*', '/')

        B.buAufgabe.setOnClickListener {
            when (B.rgLevel.checkedRadioButtonId) {
                B.rbLeicht.id -> {
                    ergw = (-10 .. 10).random()
                    var an = 0
                    while (an == 0)
                        an = (-10 .. 10).random()
                    val az = ergw * an

                    B.tvAufgabe.text = "Ganze Zahl berechnen:  $az / $an"
                    B.tvErgebnis.text = ""
                }
                B.rbMittel.id -> {
                    ergz = produkt(3)
                    ergn = produkt(3)
                    B.tvAufgabe.text = "Bruch kürzen:  $ergz / $ergn"
                    B.tvErgebnis.text = ""
                }
                else -> {
                    val az = produkt(2)
                    val an = produkt(2)
                    val bz = produkt(2)
                    val bn = produkt(2)
                    val op = ops.random()

                    B.tvAufgabe.text =
                        "Ergebnis-Bruch berechnen:  $az/$an  $op  $bz/$bn"
                    B.tvErgebnis.text = ""

                    when (op) {
                        '+' -> {
                            ergz = az * bn + an * bz
                            ergn = an * bn
                        }
                        '-' -> {
                            ergz = az * bn - an * bz
                            ergn = an * bn
                        }
                        '*' -> {
                            ergz = az * bz
                            ergn = an * bn
                        }
                        '/' -> {
                            ergz = az * bn
                            ergn = an * bz
                        }
                    }
                }
            }
        }

        B.buErgebnis.setOnClickListener {
            if(B.rbLeicht.isChecked)
                B.tvErgebnis.text = "$ergw"
            else {
                kuerzen()
                if (ergn == 1)
                    B.tvErgebnis.text = "$ergz"
                else
                    B.tvErgebnis.text = "$ergz / $ergn"
            }
        }
    }

    private fun produkt(anzahl:Int):Int
    {
        var wert = 1

        for(i in 1 .. anzahl)
            wert *= prob.random()

        return if((0 .. 1).random() == 0) wert else -wert
    }

    private fun kuerzen() {

        for(i in prim.indices)

            while(ergz%prim[i] == 0 && ergn%prim[i] == 0) {
                ergz /= prim[i]
                ergn /= prim[i]
            }

        if(ergn < 0) {
            ergz = -ergz
            ergn = -ergn
        }
    }
}
