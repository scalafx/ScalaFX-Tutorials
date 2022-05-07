package spreadsheetview

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane

object SpreadsheetViewExample2App extends JFXApp3 {
  override def start(): Unit = {
    val spv   = new SpreadsheetViewExample2()
    val stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        title = "SpreadsheetView Example 2"
        root = new BorderPane {
          padding = Insets(5)
          center = spv.spreadSheetView
          right = spv.controlGrid
        }
      }
    }
  }
}
