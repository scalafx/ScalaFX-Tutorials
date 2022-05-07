package spreadsheetview

import org.controlsfx.control.spreadsheet.{GridBase, SpreadsheetCell, SpreadsheetCellType, SpreadsheetView}
import scalafx.Includes._
import scalafx.application.{JFXApp3, Platform}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, GridPane}

object SpreadsheetViewExample3App extends JFXApp3 {
  override def start(): Unit = {
    val spv = new SpreadsheetViewExample3()
    stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        title = "SpreadsheetView Example 3"
        root = new BorderPane {
          padding = Insets(5)
          center = spv.spreadSheetView
          right = spv.controlGrid
        }
      }
    }
  }
}

class SpreadsheetViewExample3 {
  lazy val spreadSheetView: SpreadsheetView = buildSpreadsheetView()
  lazy val controlGrid    : GridPane        = buildControlGrid()

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
    rows.add(getHeader(grid, rowIndex))
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

  private def getHeader(grid: GridBase, row: Int) = {
    val title = ObservableBuffer.empty[SpreadsheetCell]
    var cell  = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, "Order ID")
    cell.setEditable(false)
    cell.getStyleClass.add("header")
    title.add(cell)
    cell = SpreadsheetCellType.STRING.createCell(row, 1, 1, 1, "Employee ID")
    cell.setEditable(false)
    cell.getStyleClass.add("header")
    title.add(cell)
    cell = SpreadsheetCellType.STRING.createCell(row, 2, 1, 1, "Product ID")
    cell.setEditable(false)
    cell.getStyleClass.add("header")
    title.add(cell)
    cell = SpreadsheetCellType.STRING.createCell(row, 3, 1, 1, "Unit Price")
    cell.setEditable(false)
    cell.getStyleClass.add("header")
    title.add(cell)
    cell = SpreadsheetCellType.STRING.createCell(row, 4, 1, 1, "Quantity")
    cell.setEditable(false)
    cell.getStyleClass.add("header")
    title.add(cell)
    for (column <- 5 until grid.getColumnCount) {
      cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "")
      cell.setEditable(false)
      title.add(cell)
    }
    title
  }

  /**
   * Build a common control Grid with some options on the left to control the SpreadsheetView
   */
  private def buildControlGrid(): GridPane = {
    val grid = new GridPane {
      hgap = 5
      vgap = 5
      padding = Insets(5, 5, 5, 5)
    }

    var row = 0

    // row header
    val rowHeaderLabel = new Label {
      text = "Row header: "
      styleClass += "property"
    }
    grid.add(rowHeaderLabel, 0, row)
    val rowHeaderCheckBox = new CheckBox {
      selected = true
      selected.onChange((_, _, newVal) => spreadSheetView.setShowRowHeader(newVal))
    }
    spreadSheetView.setShowRowHeader(rowHeaderCheckBox.selected.value)
    grid.add(rowHeaderCheckBox, 1, row)
    row += 1

    // column header
    val columnHeaderLabel = new Label {
      text = "Column header: "
      styleClass += "property"
    }
    grid.add(columnHeaderLabel, 0, row)
    val columnHeaderCheckBox = new CheckBox {
      selected = true
      selected.onChange((_, _, newVal) => spreadSheetView.setShowColumnHeader(newVal))
    }
    spreadSheetView.setShowColumnHeader(columnHeaderCheckBox.selected.value)
    grid.add(columnHeaderCheckBox, 1, row)
    row += 1

    // editable
    val editableLabel = new Label {
      text = "Editable: "
      styleClass += "property"
    }
    grid.add(editableLabel, 0, row)
    val editableCheckBox = new CheckBox {
      selected = true
    }
    spreadSheetView.setEditable(editableCheckBox.selected.value)
    grid.add(editableCheckBox, 1, row)
    spreadSheetView.editableProperty <== editableCheckBox.selected
    row += 1

    //Row Header width
    val rowHeaderWidth = new Label {
      text = "Row header width: "
      styleClass += "property"
    }
    grid.add(rowHeaderWidth, 0, row)
    val slider = new Slider(15, 100, 30)
    spreadSheetView.rowHeaderWidthProperty <== slider.value
    grid.add(slider, 1, row)
    row += 1

    //Zoom
    val zoom = new Label {
      text = "Zoom: "
      styleClass += "property"
    }
    grid.add(zoom, 0, row)
    val sliderZoom = new Slider(0.25, 2, 1)
    spreadSheetView.zoomFactorProperty <==> sliderZoom.value
    grid.add(sliderZoom, 1, row)
    row += 1

    // Multiple Selection
    val selectionModeLabel = new Label {
      text = "Multiple selection: "
      styleClass += "property"
    }
    grid.add(selectionModeLabel, 0, row)
    val selectionModeCheckBox = new CheckBox {
      selected = true
      selected.onChange { (_, _, isSelected) =>
        spreadSheetView.getSelectionModel.clearSelection()
        spreadSheetView.getSelectionModel.setSelectionMode(
          if (isSelected) SelectionMode.Multiple else SelectionMode.Single
          )
      }
    }
    grid.add(selectionModeCheckBox, 1, row)
    row += 1

    // Display selection
    val displaySelectionLabel = new Label {
      text = "Display selection: "
      styleClass.add("property")
    }
    grid.add(displaySelectionLabel, 0, row)
    val displaySelectionCheckBox = new CheckBox {
      selected = true
      selected.onChange { (_, _, isSelected) =>
        spreadSheetView.getGrid.setDisplaySelection(isSelected)
        spreadSheetView.getSelectionModel.clearSelection()
      }
    }
    grid.add(displaySelectionCheckBox, 1, row)
    row += 1

    // Select for editing
    val selectB7Button = new Button {
      text = "Select B:7"
      onAction = () =>
        Platform.runLater {
          val selectedColumn = spreadSheetView.getColumns.toList(1)
          val selectedRow    = 6
          spreadSheetView.edit(selectedRow, selectedColumn)
          spreadSheetView.getSelectionModel.focus(selectedRow, selectedColumn)
          spreadSheetView.requestFocus()
        }
    }
    grid.add(selectB7Button, 1, row)
    row += 1

    grid
  }
}
