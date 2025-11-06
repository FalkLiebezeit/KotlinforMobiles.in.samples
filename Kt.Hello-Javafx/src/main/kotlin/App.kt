import javafx.application.*
import javafx.event.*
import javafx.scene.*
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.*

fun main() {
    Application.launch(MyApplication::class.java)
}

// JavaFX-Hauptklasse
class MyApplication : Application() {

    // Initialisierung und Anzeige eines Fensters
    override fun start(primaryStage: Stage) {
        val btn = Button().apply {
            text = "Hello JavaFX!"
            onAction = EventHandler { Platform.exit() }
        }
        val root = StackPane()
        root.children.add(btn)
        primaryStage.title = "Hello JavaFX!"
        primaryStage.scene = Scene(root, 300.0, 150.0)
        primaryStage.show()
    }
}