package cell_factories

import scalafx.application.JFXApp3
import scalafx.beans.property.BooleanProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.ListView
import scalafx.scene.control.cell.CheckBoxListCell
import scalafx.scene.layout.VBox

object CheckBoxListCellDemo extends JFXApp3 {

  class Item(initialSelection: Boolean, val name: String) {
    val selected: BooleanProperty = BooleanProperty(initialSelection)

    override def toString: String = name
  }

  // Create sample data
  private val data =
    ObservableBuffer
      .from((1 to 10)
              .map(i => new Item(i % 2 == 0, s"Item $i")))

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        title = "CheckBoxListCell Demo"
        root = new VBox {
          children = Seq(
            new ListView[Item] {
              prefHeight = 250
              items = data
              cellFactory = CheckBoxListCell.forListView[Item](_.selected)
            }
            )
        }
      }
    }
  }
}
