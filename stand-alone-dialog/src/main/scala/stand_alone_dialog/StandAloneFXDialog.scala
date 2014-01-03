package stand_alone_dialog

import javafx.embed.swing.JFXPanel
import scalafx.Includes._
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.BorderPane
import scalafx.stage.Stage


/** Example of displaying an FX dialog without starting an FX application.
  * Dialog is displayed in a "non-modal" manner (thread from which dialog is displayed is not blocked).
  */
object StandAloneFXDialog extends App {

  // Shortcut to initialize JavaFX, force initialization by creating JFXPanel() object
  // (we will not use it for anything else)
  new JFXPanel()

  // Create a dialog stage and display it on JavaFX Application Thread
  Platform.runLater {

    // Create dialog
    val dialogStage = new Stage {
      outer =>
      title = "Stand-Alone Dialog"
      scene = new Scene {
        root = new BorderPane {
          padding = Insets(25)
          bottom = new Button {
            text = "Click me to close the dialog"
            onAction = handle { outer.close() }
          }
        }
      }
    }

    // Show dialog and wait till it is closed
    dialogStage.showAndWait()

    // Force application exit
    System.exit(0)
  }
}
