package org.scalafx.slick_table


import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Label, ProgressIndicator}
import scalafx.scene.layout.{BorderPane, StackPane, VBox}

/**
  * Basic example of using a table view with database interfaced through Slick API.
  * The implementation is separated into a domain model `ContactsDB`,
  * main view definition `ContactsView`, the main view model `ContactsViewModel`.
  */
object SlickTableDemo extends JFXApp {

  // Catch unhandled exceptions on FX Application thread
  Thread.currentThread().setUncaughtExceptionHandler(
    (_: Thread, ex: Throwable) => {
      ex.printStackTrace()
      new Alert(AlertType.Error) {
        initOwner(owner)
        title = "Unhandled exception"
        headerText = "Exception: " + ex.getClass + ""
        contentText = Option(ex.getMessage).getOrElse("")
      }.showAndWait()
    }
  )

  // Create application components
  private val contactsViewModel = new ContactsViewModel()
  private val contactsView = new ContactsView(contactsViewModel)

  private val glassPane = new VBox {
    children = new ProgressIndicator {
      progress = ProgressIndicator.IndeterminateProgress
      visible = true
    }
    alignment = Pos.Center
    visible = false
  }

  private val statusLabel = new Label {
    maxWidth = Double.MaxValue
    padding = Insets(0, 10, 10, 10)
  }

  private val rootView = new StackPane {
    children = Seq(
      new BorderPane {
        center = contactsView.view
        bottom = statusLabel
      },
      glassPane
    )
  }

  stage = new JFXApp.PrimaryStage {
    title = contactsView.title
    scene = new Scene(rootView)
  }

  private val taskRunner = new TaskRunner(contactsView.view, glassPane, statusLabel)
  contactsViewModel.taskRunner = taskRunner


  // Initialize database on a separate thread
  taskRunner.run(
    caption = "Setup Database",
    op = {
      contactsViewModel.setUp()
    }
  )
}
