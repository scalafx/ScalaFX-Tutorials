package hello

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane

object HelloSBT extends JFXApp {
  stage = new PrimaryStage {
    scene = new Scene {
      root = new BorderPane {
        padding = Insets(25)
        center = new Label("Hello SBT")
      }
    }
  }
}
