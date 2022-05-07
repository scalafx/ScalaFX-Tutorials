package spreadsheetview

import scalafx.Includes._
import scalafx.application.{JFXApp3, Platform}
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.layout.{BorderPane, Pane, VBox}
import scalafx.scene.{Parent, Scene}

object TableViewExample5App extends JFXApp3 {

  // Catch unhandled exceptions on FX Application thread
  Thread.currentThread().setUncaughtExceptionHandler((_: Thread, ex: Throwable) => {
    ex.printStackTrace()
    new Alert(AlertType.Error) {
      initOwner(owner)
      title = "Unhandled exception"
      headerText = "Exception: " + ex.getClass + ""
      contentText = Option(ex.getMessage).getOrElse("")
    }.showAndWait()
  })

  override def start(): Unit = {
    val spv = new TableViewExample5Example5()
    stage = new JFXApp3.PrimaryStage {
      scene = new Scene(500, 500) {
        title = "SpreadsheetView Example 5"
        root = new BorderPane {
          padding = Insets(5)
          center = spv.mainPane
          right = spv.controlPane
        }
      }
    }
  }
}

class Person(firstName_ : String, lastName_ : String) {

  val firstName = new StringProperty(this, "firstName", firstName_)
  val lastName  = new StringProperty(this, "lastName", lastName_)

  firstName.onChange { (_, oldValue, newValue) => println(s"Value changed from `$oldValue` to `$newValue`") }
  lastName.onChange { (_, oldValue, newValue) => println(s"Value changed from `$oldValue` to `$newValue`") }

  override def toString: String = firstName() + " " + lastName()
}

class TableViewExample5Example5 {

  private lazy val tableViews: Seq[TableView[Person]] = Seq(
    buildTableView(),
    buildTableView(),
    buildTableView()
    )

  private lazy val titledPanes: Seq[TitledPane] = tableViews
    .zipWithIndex
    .map { case (spv, i) =>
      new TitledPane {
        text = s"$i"
        content = spv
        //        animated = false
      }
    }

  private lazy val accordion: Accordion = new Accordion() {
    titledPanes.foreach(panes += _)
    panes.sortBy(_.text.value)
  }

  lazy val mainPane   : Parent = accordion
  lazy val controlPane: Parent = createControlPane()

  private def buildTableView(): TableView[Person] = {
    val characters: ObservableBuffer[Person] = ObservableBuffer[Person](
      new Person("Peggy", "Sue"),
      new Person("Rocky", "Raccoon")
      )
    val tableView                            = new TableView[Person](characters) {
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
    }
    tableView
  }

  private def createControlPane(): Pane = {

    /** Create a button to select a cell. */
    def button(tableNumber: Int, columnNumber: Int, rowNumber: Int): Button = {
      new Button {
        text = s"Select $tableNumber:$columnNumber:$rowNumber"
        onAction = () => {

          val tableView                              = tableViews(tableNumber)
          val selectedColumn: TableColumn[Person, _] = tableView.columns(columnNumber)
          println(s"Selected: ${selectedColumn.text}")

          // Expand selected pane
          val pane = accordion.panes(tableNumber)
          accordion.expandedPane = pane

          //          Platform.runLater {
          //            tableView.requestFocus()
          //            tableView.selectionModel.value.select(rowNumber, selectedColumn)
          //            tableView.edit(rowNumber, selectedColumn)
          //            tableView.focusModel.value.focus(rowNumber, selectedColumn)
          //          }

          // TitledPane does animation when it expends, default duration is 350ms.
          // Need to wait till that animation finishes, we will add extra 50ms to have "transition"
          // The wait need to happen off JavaFX Application Thread, so a new thread is created
          val th = new Thread(() => {
            // Wait for TitledPane animation to finish + 50ms
            Thread.sleep(350 + 50)
            Platform.runLater {
              if (!tableView.isFocused) {
                tableView.requestFocus()
              }
              // Prepare selected cell for editing
              tableView.selectionModel.value.clearAndSelect(rowNumber, selectedColumn)
              tableView.edit(rowNumber, selectedColumn)
              tableView.focusModel.value.focus(rowNumber, selectedColumn)
            }
          })
          th.setDaemon(true)
          th.start()
        }
      }
    }

    new VBox {
      spacing = 5
      padding = Insets(5, 5, 5, 5)
      children = Seq(
        button(tableNumber = 0, columnNumber = 0, rowNumber = 0),
        button(tableNumber = 1, columnNumber = 1, rowNumber = 1),
        button(tableNumber = 2, columnNumber = 0, rowNumber = 1)
        )
    }
  }
}
