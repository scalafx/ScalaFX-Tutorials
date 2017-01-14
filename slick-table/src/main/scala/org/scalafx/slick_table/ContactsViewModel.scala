package org.scalafx.slick_table

import scalafx.application.Platform
import scalafx.beans.property.{BooleanProperty, ObjectProperty}
import scalafx.collections.ObservableBuffer
import scalafx.stage.Window

/**
  * The view model. Defines UI actions and connection to the domain model - ContactsDB.
  */
class ContactsViewModel {

  var taskRunner: TaskRunner = _

  private val contactsDB = new ContactsDB()

  val parentWindow: ObjectProperty[Window] = ObjectProperty[Window](null.asInstanceOf[Window])

  // TODO: Only make public a read-only wrapper for `items`
  val items: ObservableBuffer[Person] = new ObservableBuffer[Person]()

  // Read-only collection of rows selected in the table view
  var _selectedItems: ObservableBuffer[Person] = _
  def selectedItems: ObservableBuffer[Person] = _selectedItems
  def selectedItems_=(v: ObservableBuffer[Person]): Unit = {
    _selectedItems = v
    _selectedItems.onChange {
      canRemoveRow.value = selectedItems.nonEmpty
    }
  }

  val canRemoveRow = BooleanProperty(false)

  def setUp(): Unit = {
    items.clear()
    contactsDB.setup()
    contactsDB.addSampleContent()
    items ++= contactsDB.queryPersons()
  }

  def onAddItem(): Unit = {

    val result = AddContactDialog.showAndWait(parentWindow.value)

    result match {
      case Some(person) =>
        taskRunner.run(
          caption = "Add Contact",
          op = {
            // Add new items from database
            contactsDB.add(person)
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
    taskRunner.run(
      caption = "Remove Selection",
      op = {
        contactsDB.remove(selectedItems)
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
