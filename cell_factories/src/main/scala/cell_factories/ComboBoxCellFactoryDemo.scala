package cell_factories

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.ComboBox
import scalafx.scene.layout.VBox
import scalafx.util.StringConverter

/**
 * https://groups.google.com/forum/#!topic/scalafx-users/jDuMETUx3fY
 */
object ComboBoxCellFactoryDemo extends JFXApp {

  case class Herd(name: String)

  val items: Seq[Herd] = Seq(Herd("A"), Herd("B"), Herd("C"), Herd("D"), Herd("E"))

  stage = new PrimaryStage {
    scene = new Scene(100, 50) {
      root = new VBox {
        children = new ComboBox[Herd](items) {
          converter = StringConverter.toStringConverter((h: Herd) => h.name)
          selectionModel.value.select(2)
        }
      }
    }
  }
}
