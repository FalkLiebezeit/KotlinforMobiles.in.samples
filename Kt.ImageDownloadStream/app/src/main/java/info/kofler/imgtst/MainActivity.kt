package info.kofler.imgtst

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mybutton.setOnClickListener {
            // imageView2.setImageResource(R.drawable.baden_wurttemberg)

            // im Hintergrund Foto herunterladen
            launch(Dispatchers.IO) {
                try {
                    val url = URL("https://kofler.info/uploads/foto.jpg")
                    val imgdata = url.getContent() as InputStream
                    val draw = Drawable.createFromStream(imgdata, "foto")
                    // im UI-Thread imageView aktualisieren
                    launch(Dispatchers.Main) {
                        imageView2.setImageDrawable(draw)
                    }
                } catch (e: Exception) {
                    Log.e("xxx", "Fehler $e")
                }
            }
        } // Ende onClickListener


        imageView1.setOnClickListener {
            Log.i("xxx", "Touch auf Image 1")
        }
        imageView1.setOnLongClickListener {
            Log.i("xxx", "Langer Touch auf Image 1")
            true
        }
    }
}
