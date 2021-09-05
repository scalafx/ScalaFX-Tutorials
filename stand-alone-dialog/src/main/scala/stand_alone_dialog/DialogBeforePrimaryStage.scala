package stand_alone_dialog

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane
import scalafx.stage.{FileChooser, Stage}

/**
 * Example of displaying a dialog before primary stage is displayed.
 */
object DialogBeforePrimaryStage extends JFXApp3 {

  override def start(): Unit = {
    // Do something with command line arguments here we print them
    // Named arguments have a form: --name=value
    // For instance, "--output=alpha beta" is interpreted as named argument "output" with value "alpha"
    // and unnamed parameter "beta".
    println("Commend line arguments:\n" +
              "  unnamed: " + parameters.unnamed.mkString("[", ", ", "]") + "\n" +
              "  named  : " + parameters.named.mkString("[", ", ", "]"))

    // Show a dialog before primary stage is constructed
    val fileChooser = new FileChooser()
    val file        = Option(fileChooser.showOpenDialog(new Stage()))

    // Primary stage is provided by JavaFX runtime, but you can create other stages of you need to.
    stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        root = new BorderPane {
          padding = Insets(25)
          center = new Label {
            text = file match {
              case Some(f) => "Selected file: " + f.getPath
              case None => "File not selected."
            }
          }
        }
      }
    }
  }
}
