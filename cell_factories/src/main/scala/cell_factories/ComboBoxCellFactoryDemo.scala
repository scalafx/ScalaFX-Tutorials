package cell_factories

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.ComboBox
import scalafx.scene.layout.VBox
import scalafx.util.StringConverter

/**
 * https://groups.google.com/forum/#!topic/scalafx-users/jDuMETUx3fY
 */
object ComboBoxCellFactoryDemo extends JFXApp3 {

  case class Herd(name: String)

  override def start(): Unit = {
    val items: Seq[Herd] = Seq(Herd("A"), Herd("B"), Herd("C"), Herd("D"), Herd("E"))

    stage = new JFXApp3.PrimaryStage {
      scene = new Scene(200, 100) {
        title = "ComboBox CellFactory Demo"
        root = new VBox {
          children = new ComboBox[Herd](items) {
            converter = StringConverter.toStringConverter((h: Herd) => h.name)
            selectionModel.value.select(2)
          }
          padding = Insets(14, 14, 14, 14)
        }
      }
    }
  }
}
