package cell_factories

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text

/**
 * Example for StackOverflow question "How to color each character differently in a TableView TableCell"
 * [[https://stackoverflow.com/questions/41752376/how-to-color-each-character-differently-in-a-tableview-tablecell]]
 */
object ColorCharactersInTableView2 extends JFXApp {

  class Person(firstName_ : String, lastName_ : String) {

    val firstName = new StringProperty(this, "firstName", firstName_)
    val lastName = new StringProperty(this, "lastName", lastName_)

    override def toString: String = firstName() + " " + lastName()
  }

  private val characters = ObservableBuffer[Person](
    new Person("Peggy", "Sue"),
    new Person("Rocky", "Raccoon"),
    new Person("Bungalow Bill", "Bill")
  )

  private val tc = new TableColumn[Person, String] {
    text = "First Name"
    cellValueFactory = {
      _.value.firstName
    }

    cellFactory = { _: TableColumn[Person, String] =>
      new TableCell[Person, String] {
        item.onChange { (_, _, newValue) =>
          Option(newValue) match {
            case Some(value) =>
              val texts = value.iterator.map { c =>
                val color: Color = c.toLower match {
                  case 'r' => Color.Red
                  case 'g' => Color.Green
                  case 'b' => Color.Blue
                  case _ => Color.Black
                }
                new Text {
                  text = c.toString
                  fill = color
                }
              }.toSeq
              graphic = new HBox(texts: _*)
            case None =>
              text = null
              graphic = null
          }
        }
      }
    }

    prefWidth = 180
  }


  stage = new PrimaryStage {
    title = "Editable Table View"
    scene = new Scene {
      root = new VBox {
        children = Seq(
          new TableView[Person](characters) {
            columns ++= Seq(
              tc,
              new TableColumn[Person, String]() {
                text = "Last Name"
                cellValueFactory = {
                  _.value.lastName
                }
                cellFactory = TextFieldTableCell.forTableColumn[Person]()
                prefWidth = 180
              }
            )
          }
        )
      }
    }
  }
}