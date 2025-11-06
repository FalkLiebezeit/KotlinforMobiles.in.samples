import javafx.application.*
import javafx.scene.*
import javafx.scene.canvas.*
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.stage.*

fun main() {
    Application.launch(MyApplication::class.java)
}

// JavaFX-Hauptklasse
class MyApplication : Application() {

    // Initialisierung und Anzeige eines Fensters
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Grafik mit JavaFX"
         // Zeichenbereich
        val canvas = Canvas(400.0, 400.0)
        // Inhalt zeichnen
        drawShapes(canvas.graphicsContext2D)
        // Canvas in das Fenster einbauen
        val root = Group()
        root.children.add(canvas)
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    private fun drawShapes(gc: GraphicsContext) {
        // Linie, standardmäßig schwarz, 1 Pixel breit
        gc.strokeLine(10.0, 10.0, 200.0, 20.0)

        // rot, 5 Pixel breit
        gc.stroke = Color.RED
        gc.lineWidth = 5.0
        gc.strokeLine(10.0, 30.0, 200.0, 40.0)

        // grün, mit runden Endpunkten
        gc.stroke = Color.GREEN
        gc.lineCap = StrokeLineCap.ROUND
        gc.strokeLine(10.0, 50.0, 200.0, 60.0)

        // einige Kreise
        for (r in 20 until 90 step 10 )
            gc.strokeOval(110.0 - r, 150.0 - r, 2.0 * r, 2.0 * r)

        // gefülltes Polgon
        val x = doubleArrayOf(240.0, 360.0, 300.0, 360.0, 220.0)
        val y = doubleArrayOf(40.0,  60.0, 150.0, 290.0, 240.0)

        // Polygon gelb füllen ...
        gc.fill = Color.YELLOW
        gc.fillPolygon(x, y, x.size)

        // ... und blau umranden
        gc.stroke = Color.BLUE
        gc.lineJoin = StrokeLineJoin.ROUND
        gc.strokePolygon(x, y, x.size)

    }
}