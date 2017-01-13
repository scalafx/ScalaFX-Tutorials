package org.scalafx.slick_table

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.beans.property.{BooleanProperty, ObjectProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.TableView.TableViewSelectionModel
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.stage.Window

/**
  * The view model. Defines UI actions and connection to the domain model - ContactsDB.
  */
class ContactsViewModel {

  var taskRunner: TaskRunner = _

  private val contactsDB = new ContactsDB()

  val parentWindow: ObjectProperty[Window] = ObjectProperty[Window](null.asInstanceOf[Window])

  private var _selectionModel: TableView.TableViewSelectionModel[Person] = _

  def selectionModel: TableViewSelectionModel[Person] = _selectionModel
  def selectionModel_=(v: TableView.TableViewSelectionModel[Person]): Unit = {
    _selectionModel = v
    _selectionModel.selectedItems.onChange { (_, _) =>
      canRemoveRow.value = !_selectionModel.isEmpty
    }
  }

  // TODO: Only make public a read-only wrapper for `items`
  val items: ObservableBuffer[Person] = new ObservableBuffer[Person]()

  val canRemoveRow = BooleanProperty(false)

  def setUp(): Unit = {
    items.clear()
    contactsDB.setup()
    contactsDB.addSampleContent()
    items ++= contactsDB.queryPersons()
  }


  def onAddItem(): Unit = {
    // Create the custom dialog.
    val dialog = new Dialog[Person]() {
      initOwner(parentWindow.get)
      title = "Add New Contact"
      headerText = "Enter contact details"
    }

    // Set the button types.
    dialog.dialogPane().buttonTypes = Seq(ButtonType.OK, ButtonType.Cancel)

    // Create the username and password labels and fields.
    val firstNameTextField = new TextField()
    val lastNameTextField = new TextField()
    val emailTextField = new TextField()

    dialog.dialogPane().content = new GridPane {
      hgap = 10
      vgap = 10
      padding = Insets(20, 100, 10, 10)

      add(new Label("First name:"), 0, 0)
      add(firstNameTextField, 1, 0)
      add(new Label("Last name:"), 0, 1)
      add(lastNameTextField, 1, 1)
      add(new Label("email:"), 0, 2)
      add(emailTextField, 1, 2)
    }

    // Enable/Disable OK button depending on whether all data was entered.
    val okButton = dialog.dialogPane().lookupButton(ButtonType.OK)
    // Simple validation that sufficient data was entered
    okButton.disable <== (firstNameTextField.text.isEmpty ||
      lastNameTextField.text.isEmpty || emailTextField.text.isEmpty)

    // Request focus on the first name field by default.
    Platform.runLater(firstNameTextField.requestFocus())

    // When the OK button is clicked, convert the result to a Person.
    dialog.resultConverter = dialogButton =>
      if (dialogButton == ButtonType.OK)
        Person(None, firstNameTextField.text(), lastNameTextField.text(), emailTextField.text())
      else
        null

    val result = dialog.showAndWait()

    result match {
      case Some(Person(is, first, last, email)) =>
        taskRunner.run(
          caption = "Add Contact",
          op = {
            // Add new items from database
            contactsDB.add(Person(is, first, last, email))
            // Return items from database
            val updatedItems = contactsDB.queryPersons()
            // Update items on FX thread
            Platform.runLater {
              updateItems(updatedItems)
            }
          }
        )
      case _ =>
    }
  }

  def onRemove(): Unit = {
    val selection = selectionModel.selectedItems
    taskRunner.run(
      caption = "Remove Selection",
      op = {
        contactsDB.remove(selection)
        // Return items from database
        val updatedItems = contactsDB.queryPersons()
        // Update items on FX thread
        Platform.runLater {
          updateItems(updatedItems)
        }
      }
    )
  }

  def onReset(): Unit = {
    taskRunner.run(
      caption = "Reset DB",
      op = {
        contactsDB.clear()
        contactsDB.addSampleContent()
        // Return items from database
        val updatedItems = contactsDB.queryPersons()
        // Update items on FX thread
        Platform.runLater {
          updateItems(updatedItems)
        }
      }
    )
  }

  private def updateItems(updatedItems: Seq[Person]): Unit = {
    val toAdd = updatedItems.diff(items)
    val toRemove = items.diff(updatedItems)
    items ++= toAdd
    items --= toRemove
  }
}
