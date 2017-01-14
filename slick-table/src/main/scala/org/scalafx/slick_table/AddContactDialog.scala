package org.scalafx.slick_table

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.stage.Window

/**
  * Creates and displays an Add Contact dialog.
  */
object AddContactDialog {

  def showAndWait(parentWindow: Window): Option[Person] = {
    // Create the custom dialog.
    val dialog = new Dialog[Person]() {
      initOwner(parentWindow)
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

    // Clean up result type
    result match {
      case Some(Person(is, first, last, email)) => Some(Person(is, first, last, email))
      case _ => None
    }
  }

}
