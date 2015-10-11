package samEventHandlers

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox

object SAMDemo extends JFXApp {

  stage = new PrimaryStage {
    title = "SAM Demo"
    scene = new Scene {
      root = new HBox {
        children = Seq(
          new Button {
            text = "Print message"
            onAction = { ae => println("some message") }
          }
        )
      }
    }
  }
}
