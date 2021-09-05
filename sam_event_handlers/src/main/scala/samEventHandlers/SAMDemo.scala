package samEventHandlers

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox

object SAMDemo extends JFXApp3 {

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "SAM Demo"
      scene = new Scene {
        root = new HBox {
          children = Seq(
            new Button {
              text = "Print message"
              onAction = _ => println("some message")
            }
            )
        }
      }
    }
  }
}
