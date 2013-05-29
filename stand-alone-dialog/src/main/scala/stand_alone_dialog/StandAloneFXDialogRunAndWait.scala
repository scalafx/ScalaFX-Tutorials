package stand_alone_dialog

import javafx.embed.swing.JFXPanel
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.BorderPane
import scalafx.stage.Stage


/** Example of displaying an FX dialog without starting an FX application.
  * Dialog is displayed in a "modal" manner (blocks the invoking thread till dialog is closed).
  * Blocking of the invoking thread is done using `FXUtils.runAndWait` method.
  */
object StandAloneFXDialogRunAndWait extends App {

  // Shortcut to initialize JavaFX, force initialization by creating JFXPanel() object
  // (we will not use it for anything else)
  new JFXPanel()

  // Create the first dialog stage and display it on JavaFX Application Thread
  // Wait for the dialog to close before proceeding
  FXUtils.runAndWait {
    showInDialog("Click me to close the FIRST dialog")
  }

  println("First dialog closed.")

  // Create the second dialog stage and display it on JavaFX Application Thread
  // Wait for the dialog to close before proceeding
  FXUtils.runAndWait {
    showInDialog("Click me to close the SECOND dialog")
  }

  println("First dialog closed.")

  // Force application exit
  System.exit(0)


  /** Show a `message` in a dialog box, wait till dialog is closed */
  private def showInDialog(message: String) {
    // Create dialog
    val dialogStage = new Stage {
      outer =>
      title = "Stand-Alone Dialog - runAndWait"
      scene = new Scene {
        root = new BorderPane {
          padding = Insets(25)
          bottom = new Button {
            text = message
            onAction = outer.close
          }
        }
      }
    }

    // Show dialog and wait till it is closed
    dialogStage.showAndWait
  }
}
