package cell_factories

import javafx.scene.{control => jfxsc}
import javafx.{util => jfxu}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableColumn, TableView}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.scene.{Node, Scene}

/**
 * Example for StackOverflow question "How to color each character differently in a TableView TableCell"
 * [[https://stackoverflow.com/questions/41752376/how-to-color-each-character-differently-in-a-tableview-tablecell]]
 */
object ColorCharactersInTableView extends JFXApp {

  class Person(firstName_ : String, lastName_ : String) {
    val firstName = new StringProperty(this, "firstName", firstName_)
    val lastName = new StringProperty(this, "lastName", lastName_)
  }

  private val characters = ObservableBuffer[Person](
    new Person("Peggy", "Sue"),
    new Person("Rocky", "Raccoon"),
    new Person("Bungalow Bill", "Bill")
  )

  /** Render string as colored text */
  def createColorText(name: String): Node = {
    val texts = name.map { char =>
      val color = char.toLower match {
        case 'r' => Color.Red
        case 'g' => Color.Green
        case 'b' => Color.Blue
        case _ => Color.Black
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
    // Create cell factory JavaFX way
    cellFactory = new jfxu.Callback[jfxsc.TableColumn[Person, String], jfxsc.TableCell[Person, String]] {
      override def call(p: jfxsc.TableColumn[Person, String]): jfxsc.TableCell[Person, String] = {
        new jfxsc.TableCell[Person, String] {
          override def updateItem(item: String, empty: Boolean): Unit = {
            super.updateItem(item, empty)
            setText(null)
            val graphic = if (item != null && !empty) createColorText(item) else null
            setGraphic(graphic)
          }
        }
      }
    }
    prefWidth = 180
  }

  private val lastNameColumn = new TableColumn[Person, String]() {
    text = "Last Name"
    cellValueFactory = _.value.lastName
    // TableColumn renders cell values as String by default, so we do not need call factory here
    prefWidth = 180
  }


  stage = new PrimaryStage {
    title = "Table View with Color Text - JavaFX way"
    scene = new Scene {
      root = new VBox {
        children = Seq(
          new TableView[Person](characters) {
            columns ++= Seq(firstNameColumn, lastNameColumn)
          }
        )
      }
    }
  }
}