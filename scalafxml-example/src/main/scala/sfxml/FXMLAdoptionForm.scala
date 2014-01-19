package sfxml

import java.io.IOException
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{NoDependencyResolver, FXMLView}


/** Example of using FXMLLoader from ScalaFX.
  *
  * @author Jarek Sacha
  */
object FXMLAdoptionForm extends JFXApp {

  val resource = getClass.getResource("AdoptionForm.fxml")
  if (resource == null) {
    throw new IOException("Cannot load resource: AdoptionForm.fxml")
  }

  val root = FXMLView(resource, NoDependencyResolver)

  stage = new PrimaryStage() {
    title = "FXML GridPane Demo"
    scene = new Scene(root)
  }

}
