package com.example.eingabedatumuhrzeit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eingabedatumuhrzeit.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var B: ActivityMainBinding

    private val datumSDF = SimpleDateFormat(
        "dd.MM.yyyy", Locale.getDefault())
    private val uhrzeitSDF = SimpleDateFormat(
        "HH:mm:ss", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = ActivityMainBinding.inflate(layoutInflater)
        setContentView(B.root)

        val jetzt = Calendar.getInstance().time
        B.etDatum.setText(datumSDF.format(jetzt))
        B.etUhrzeit.setText(uhrzeitSDF.format(jetzt))

        B.buDatum.setOnClickListener {
            val eingabe = B.etDatum.text.toString()
            val teile = eingabe.split(".")

            if(teile.size != 3 || teile[0].isEmpty() ||
                teile[1].isEmpty() || teile[2].length != 4) {
                B.tvDatum.text = "Bitte gemäß Format dd.MM.yyyy"
            }
            else {
                val eingabeSDF = SimpleDateFormat(
                    "dd.MM.yyyy", Locale.getDefault())
                var ausgabe = "Datum: "

                try {
                    val datum = eingabeSDF.parse(eingabe)
                    val kal = Calendar.getInstance()
                    kal.time = datum as Date
                    ausgabe += datumSDF.format(kal.time)
                }
                catch (ex:Exception) {
                    ausgabe += "nicht gültig"
                }

                B.tvDatum.text = ausgabe
            }
        }

        B.buUhrzeit.setOnClickListener  {
            val eingabe = B.etUhrzeit.text.toString()
            val eingabeSDF = SimpleDateFormat(
                "HH:mm:ss", Locale.getDefault())
            var ausgabe = "Uhrzeit: "

            try {
                val uhrzeit = eingabeSDF.parse(eingabe)
                val kal = Calendar.getInstance()
                kal.time = uhrzeit as Date
                ausgabe += uhrzeitSDF.format(kal.time)
            }
            catch (ex:Exception) {
                ausgabe += "nicht gültig"
            }

            B.tvUhrzeit.text = ausgabe
        }
    }
}