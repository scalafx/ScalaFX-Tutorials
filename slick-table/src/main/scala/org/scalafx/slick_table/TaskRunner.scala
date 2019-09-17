package org.scalafx.slick_table

import javafx.{concurrent => jfxc}

import scalafx.application.Platform
import scalafx.scene.Node
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Label}

/**
  * Runs a background task disabling the `mainView` and main visible `glassPane`.
  * Shows statis using `statusLabel`.
  */
class TaskRunner(mainView: Node,
                 glassPane: Node,
                 statusLabel: Label) {

  /**
    * Run an operation on a separate thread. Return and wait for its completion,
    * then return result of running that operation.
    *
    * A progress indicator is displayed while running the operation.
    *
    * @param caption name for the thread (useful in debugging) and status displayed
    *                when running the task.
    * @param op      operation to run.
    * @tparam R type of result returned by the operation.
    * @return result returned by operation `op`.
    */
  def run[R](caption: String,
             op: => R): Unit = {

    def showProgress(progressEnabled: Boolean): Unit = {
      mainView.disable = progressEnabled
      glassPane.visible = progressEnabled
    }

    // Indicate task in progress
    Platform.runLater {
      showProgress(true)
      statusLabel.text = caption
    }

    val task = new jfxc.Task[R] {
      override def call(): R = {
        op
      }
      override def succeeded(): Unit = {
        showProgress(false)
        statusLabel.text = caption + " - Done."
        // Do callback, of defined
      }
      override def failed(): Unit = {

        showProgress(false)
        statusLabel.text = caption + " - Failed."
        val t = Option(getException)
        t.foreach(_.printStackTrace())
        // Show error message
        new Alert(AlertType.Error) {
          initOwner(owner)
          title = caption
          headerText = "Operation failed. " + t.map("Exception: " + _.getClass).getOrElse("")
          contentText = t.map(_.getMessage).getOrElse("")
        }.showAndWait()
      }
      override def cancelled(): Unit = {
        showProgress(false)
        statusLabel.text = caption + " - Cancelled."
      }
    }

    val th = new Thread(task, caption)
    th.setDaemon(true)
    th.start()
  }

}
