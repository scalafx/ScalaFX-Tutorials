package cell_factories

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.control.{Button, TableColumn, TableView}
import scalafx.scene.layout.VBox

/**
 * Example for StackOverflow question "how to create an updateable tableview cell in Scala"
 * [[https://stackoverflow.com/questions/33733341/how-to-create-an-updateable-tableview-cell-in-scala]]
 */
object EditableTableView extends JFXApp3 {

  class Person(firstName_ : String, lastName_ : String) {

    val firstName = new StringProperty(this, "firstName", firstName_)
    val lastName  = new StringProperty(this, "lastName", lastName_)

    firstName.onChange { (_, oldValue, newValue) => println(s"Value changed from `$oldValue` to `$newValue`") }
    lastName.onChange { (_, oldValue, newValue) => println(s"Value changed from `$oldValue` to `$newValue`") }

    override def toString: String = firstName() + " " + lastName()
  }

  override def start(): Unit = {
    val characters = ObservableBuffer[Person](
      new Person("Peggy", "Sue"),
      new Person("Rocky", "Raccoon")
      )

    stage = new JFXApp3.PrimaryStage {
      title = "Editable Table View"
      scene = new Scene {
        root = new VBox {
          children = Seq(
            new TableView[Person](characters) {
              editable = true
              columns ++= List(
                new TableColumn[Person, String] {
                  text = "First Name"
                  cellValueFactory = {
                    _.value.firstName
                  }
                  cellFactory = TextFieldTableCell.forTableColumn[Person]()
                  prefWidth = 180
                },
                new TableColumn[Person, String]() {
                  text = "Last Name"
                  cellValueFactory = {
                    _.value.lastName
                  }
                  cellFactory = TextFieldTableCell.forTableColumn[Person]()
                  prefWidth = 180
                }
                )
            },
            new Button {
              text = "Print content"
              onAction = () => {
                println("Characters:")
                characters.foreach(println)
              }
            }
            )
        }
      }
    }
  }
}
