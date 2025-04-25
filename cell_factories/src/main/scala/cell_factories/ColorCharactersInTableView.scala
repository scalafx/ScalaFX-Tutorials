package cell_factories

import scalafx.application.JFXApp3
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableColumn, TableView}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.scene.{Node, Scene}

object ColorCharactersInTableView extends JFXApp3 {

  class Person(firstName_ : String, lastName_ : String) {
    val firstName = new StringProperty(this, "firstName", firstName_)
    val lastName  = new StringProperty(this, "lastName", lastName_)
  }

  private val characters = ObservableBuffer[Person](
    new Person("Peggy", "Sue"),
    new Person("Rocky", "Raccoon"),
    new Person("Bill", "Bungalow")
  )

  /** Render string as colored text */
  def createColorText(name: String): Node = {
    val texts = name.map { char =>
      val color = char.toLower match {
        case 'r' => Color.Red
        case 'g' => Color.Green
        case 'b' => Color.Blue
        case _   => Color.Black
      }
      new Text {
        text = char.toString
        fill = color
      }
    }
    new HBox(texts: _*)
  }

  private val firstNameColumn = new TableColumn[Person, String] {
    text = "First Name"
    cellValueFactory = _.value.firstName
    cellFactory = (cell, value) => {
      cell.graphic = createColorText(value)
    }
    prefWidth = 180
  }

  private val lastNameColumn = new TableColumn[Person, String]() {
    text = "Last Name"
    cellValueFactory = _.value.lastName
    // TableColumn renders cell values as String by default, so we do not need call factory here
    prefWidth = 180
  }

  override def start(): Unit = {
    val tableView = new TableView[Person]() {
      columns ++= Seq(firstNameColumn, lastNameColumn)
    }
    stage = new JFXApp3.PrimaryStage {
      title = "Table View with Color Text - ScalaFX way"
      scene = new Scene {
        root = new VBox {
          children = Seq(tableView)
        }
      }
    }

    tableView.items = characters
  }
}
