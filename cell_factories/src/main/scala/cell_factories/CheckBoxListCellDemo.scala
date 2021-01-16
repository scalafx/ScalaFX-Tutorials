package cell_factories

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.BooleanProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.cell.CheckBoxListCell
import scalafx.scene.control.{Button, ListView}
import scalafx.scene.layout.VBox

import scala.language.implicitConversions

object CheckBoxListCellDemo extends JFXApp {

  class Item(initialSelection: Boolean, val name: String) {
    val selected: BooleanProperty = BooleanProperty(initialSelection)

    override def toString: String = name
  }

  private val data = ObservableBuffer[Item](
    (1 to 10).map { i => new Item(i % 2 == 0, s"Item $i") }
  )

  stage = new PrimaryStage {
    scene = new Scene {
      title = "CheckBoxListCell Demo"
      root = new VBox {
        children = Seq(
          new ListView[Item] {
            prefHeight = 250
            items = data
            cellFactory = CheckBoxListCell.forListView(_.selected)
          },
          new Button("Print State ") {
            onAction = () => {
              println("-------------")
              println(data.map(d => d.name + ": " + d.selected()).mkString("\n"))
            }
          }
        )
      }
    }
  }
}

