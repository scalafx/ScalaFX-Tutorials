package hello

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane

object HelloSBT extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        root = new BorderPane {
          padding = Insets(25)
          center = new Label("Hello SBT")
        }
      }
    }
  }
}
