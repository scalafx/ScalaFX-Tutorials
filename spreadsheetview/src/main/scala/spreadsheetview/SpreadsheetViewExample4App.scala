package spreadsheetview

import org.controlsfx.control.spreadsheet.{GridBase, SpreadsheetCell, SpreadsheetCellType, SpreadsheetView}
import scalafx.Includes._
import scalafx.application.{JFXApp3, Platform}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, Pane, VBox}
import scalafx.scene.{Parent, Scene}

object SpreadsheetViewExample4App extends JFXApp3 {

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
    val spv = new SpreadsheetViewExample4()
    stage = new JFXApp3.PrimaryStage {
      scene = new Scene(500, 500) {
        title = "SpreadsheetView Example 4"
        root = new BorderPane {
          padding = Insets(5)
          center = spv.mainPane
          right = spv.controlPane
        }
      }
    }
  }
}

class SpreadsheetViewExample4 {
  private lazy val spreadSheetViews: Seq[SpreadsheetView] = Seq(
    buildSpreadsheetView(),
    buildSpreadsheetView(),
    buildSpreadsheetView()
    )

  private lazy val titledPanes: Seq[TitledPane] = spreadSheetViews
    .zipWithIndex
    .map { case (spv, i) =>
      new TitledPane {
        text = s"${i + 1}"
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

  accordion.panes.foreach {
    accordion.expandedPane = _
  }

  private def buildSpreadsheetView(): SpreadsheetView = {
    val rowCount    = 31 //Will be re-calculated after if incorrect.
    val columnCount = 5

    val grid            = buildGrid(rowCount, columnCount)
    val spreadSheetView = new SpreadsheetView(grid)
    spreadSheetView.getStylesheets += this.getClass.getResource("spreadsheetSample2.css").toExternalForm
    spreadSheetView.setShowRowHeader(false)
    spreadSheetView.setShowColumnHeader(false)

    spreadSheetView
  }

  private def buildGrid(rowCount: Int, columnCount: Int): GridBase = {
    val grid     = new GridBase(rowCount, columnCount)
    val rows     = new ObservableBuffer[ObservableBuffer[SpreadsheetCell]]()
    var rowIndex = 0
    rows.add(createHeader(rowIndex))
    rowIndex += 1

    for (i <- rowIndex until rowIndex + 100) {
      rows += {
        val randomRow = ObservableBuffer.empty[SpreadsheetCell]
        randomRow += SpreadsheetCellType.INTEGER.createCell(i, 0, 1, 1, (Math.random * 100).toInt)
        randomRow += SpreadsheetCellType.INTEGER.createCell(i, 1, 1, 1, i)
        randomRow += SpreadsheetCellType.INTEGER.createCell(i, 2, 1, 1, (Math.random * 100).toInt)
        randomRow += {
          val cell = SpreadsheetCellType.DOUBLE.createCell(i, 3, 1, 1, Math.random * 100)
          cell.setFormat("##.##")
          cell
        }
        randomRow += SpreadsheetCellType.INTEGER.createCell(i, 4, 1, 1, (Math.random * 2).toInt)
        for (column <- 5 until grid.getColumnCount) {
          randomRow.add(SpreadsheetCellType.STRING.createCell(i, column, 1, 1, ""))
        }
        randomRow
      }
    }
    // Map to JavaFX `delegate` to provide type expected by Grid
    grid.setRows(rows.map(_.delegate))
    grid
  }

  private def createHeader(row: Int): ObservableBuffer[SpreadsheetCell] = {
    def header(column: Int, value: String): SpreadsheetCell = {
      val cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, value)
      cell.setEditable(false)
      cell.getStyleClass.add("header")
      cell
    }

    val headings = Seq("Order ID", "Employee ID", "Product ID", "Unit Price", "Quantity")
    ObservableBuffer.from(headings.zipWithIndex.map(h => header(h._2, h._1)))
  }

  private def createControlPane(): Pane = {

    /** Create a button to select a cell. */
    def button(sheetNumber: Int, columnNumber: Int, rowNumber: Int): Button = {
      new Button {
        text = s"Select ${sheetNumber + 1}:${(Char.char2int('A') + columnNumber).toChar}:${rowNumber + 1}"
        onAction = () =>
          Platform.runLater {

            val sheet          = spreadSheetViews(sheetNumber)
            val selectedColumn = sheet.getColumns.toList(columnNumber)

            // Expand selected pane
            val pane = accordion.panes(sheetNumber)
            accordion.expandedPane = pane

            //          sheet.getSelectionModel.clearAndSelect(rowNumber, selectedColumn)
            //          sheet.getSelectionModel.focus(rowNumber, selectedColumn)
            //          sheet.requestFocus()
            //          sheet.edit(rowNumber, selectedColumn)

            // TitledPane does animation when it expends, default duration is 350ms.
            // Need to wait till that animation finishes, we will add extra 50ms to have "transition"
            // The wait need to happen off JavaFX Application Thread, so a new thread is created
            val th = new Thread(() => {
              // Wait for TitledPane animation to finish + 50ms
              Thread.sleep(350 + 50)
              Platform.runLater {
                // Prepare selected cell for editing
                sheet.getSelectionModel.clearAndSelect(rowNumber, selectedColumn)
                sheet.edit(rowNumber, selectedColumn)
                sheet.getSelectionModel.focus(rowNumber, selectedColumn)
                sheet.requestFocus()
              }
            })
            th.setDaemon(true)
            th.start()

            //          if (sheet.isVisible) {
            //            sheet.getSelectionModel.clearAndSelect(rowNumber, selectedColumn)
            //            sheet.edit(rowNumber, selectedColumn)
            //            sheet.getSelectionModel.focus(rowNumber, selectedColumn)
            //            sheet.requestFocus()
            //          }
            //
            //          sheet.visible.onChange { (_, _, newValue) =>
            //            if (newValue) {
            //              sheet.getSelectionModel.clearAndSelect(rowNumber, selectedColumn)
            //              sheet.edit(rowNumber, selectedColumn)
            //              sheet.getSelectionModel.focus(rowNumber, selectedColumn)
            //              sheet.requestFocus()
            //            }
            //          }

          }
      }
    }

    new VBox {
      spacing = 5
      padding = Insets(5, 5, 5, 5)
      children = Seq(
        button(sheetNumber = 0, columnNumber = 1, rowNumber = 6),
        button(sheetNumber = 1, columnNumber = 4, rowNumber = 2),
        button(sheetNumber = 2, columnNumber = 2, rowNumber = 4)
        )
    }
  }
}
