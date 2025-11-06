
import javafx.geometry.Pos
import tornadofx.*

fun main() {
    // TornadoFX-Programm starten
    launch<MyApp>()
}

// zentrale App-Klasse
class MyApp: App(MyView::class)

// Fensterinhalt
class MyView: View() {
    // VBox: mehrere Steuerelemente untereinander anordnen
    override val root = vbox {
        alignment = Pos.CENTER      // zentrieren
        title = "Hello TornadoFX!"  // Fenstertitel

        // Beschriftungsfeld
        label("Hello TornadoFX!") {
            vboxConstraints {
                // 10 Pixel Abstand rundherum
                margin = insets(10)
            }
        }

        // Button
        button("Ende") {
            vboxConstraints {
                // 10 Pixel Abstand rundherum
                margin = insets(10)
            }
            // beim Anklicken: Programm beenden
            action {
                close()  // Programmende
            }
        }
    }
}