package sfxml

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

import java.io.IOException

/**
 * Example of using FXMLLoader from ScalaFX.
 *
 * @author Jarek Sacha
 */
object FXMLAdoptionForm extends JFXApp3 {
  override def start(): Unit = {

    val resource = getClass.getResource("AdoptionForm.fxml")
    if (resource == null) {
      throw new IOException("Cannot load resource: AdoptionForm.fxml")
    }

    val root = FXMLView(resource, NoDependencyResolver)

    stage = new JFXApp3.PrimaryStage() {
      title = "FXML GridPane Demo"
      scene = new Scene(root)
    }
  }
}
