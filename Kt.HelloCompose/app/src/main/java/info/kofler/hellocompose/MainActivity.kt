package info.kofler.hellocompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import info.kofler.hellocompose.ui.HelloComposeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        Greeting("Android")
                        ButtonCounter()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloComposeTheme {
        Greeting("Android")
    }
}

// ButtonCounter-Variante mit Delegation
@Preview(showBackground = true)
@Composable
fun ButtonCounter() {
    var cnt by remember { mutableStateOf(0) }

    Column {
        Button(content = { Text("Weiterzählen") },
            onClick = { cnt += 1 })
        Text("Der Button wurde ${cnt}-mal gedrückt.")
    }
}

// ButtonCounter-Variante ohne Delegation
@Composable
fun ButtonCounterWithoutDelegation() {
    val cnt = remember { mutableStateOf(0) }
    Column {
        Button(content = { Text("Weiterzählen") },
               onClick = { cnt.value += 1 })
        Text("Der Button wurde ${cnt.value}-mal gedrückt.")
    }
}