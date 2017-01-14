package org.scalafx.slick_table

import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Parent
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Font

/**
  * Created main view and connects it to the view model.
  */
class ContactsView(val model: ContactsViewModel) {

  val title = "Address Book"

  private val table: TableView[Person] = {
    // Define columns
    val firstNameColumn = new TableColumn[Person, String] {
      text = "First name"
      cellValueFactory = {
        _.value.firstName
      }
      prefWidth = 180
    }

    val lastNameColumn = new TableColumn[Person, String] {
      text = "Last name"
      cellValueFactory = {
        _.value.lastName
      }
      prefWidth = 180
    }

    val emailColumn = new TableColumn[Person, String] {
      text = "email address"
      cellValueFactory = {
        _.value.emailAddress
      }
      prefWidth = 250
    }

    // Build the table
    new TableView[Person](model.items) {
      columns += (firstNameColumn, lastNameColumn, emailColumn)
      margin = Insets(10, 0, 10, 0)
    }
  }

  model.selectedItems = table.selectionModel.value.selectedItems

  private val addButton = new Button {
    text = "Add"
    onAction = handle {
      model.onAddItem()
    }
  }

  private val removeButton = new Button {
    text = "Remove"
    disable <== !model.canRemoveRow
    onAction = handle {
      model.onRemove()
    }
  }

  private val resetButton = new Button {
    text = "Reset"
    onAction = handle {
      model.onReset()
    }
  }

  val view: Parent = {

    val label = new Label(title) {
      font = Font("Arial", 20)
    }

    val buttonBar = new ButtonBar {
      buttons = Seq(addButton, removeButton, resetButton)
    }

    new BorderPane {
      top = label
      center = table
      bottom = buttonBar
      padding = Insets(10, 10, 10, 10)
    }
  }

}
