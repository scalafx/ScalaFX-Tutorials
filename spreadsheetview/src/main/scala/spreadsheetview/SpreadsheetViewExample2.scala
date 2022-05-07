package spreadsheetview

import org.controlsfx.control.spreadsheet._
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.layout.GridPane

import java.awt.Desktop
import java.io.IOException
import java.net.{URI, URISyntaxException}

/**
 * Example adapted from ControlsFX sample "org.controlsfx.samples.spreadsheet.HelloSpreadsheetView2.java"
 */
class SpreadsheetViewExample2 {
  lazy val spreadSheetView: SpreadsheetView = buildSpreadsheetView()
  lazy val controlGrid    : GridPane        = buildControlGrid()


  private def buildSpreadsheetView(): SpreadsheetView = {
    val rowCount    = 31 //Will be re-calculated after if incorrect.
    val columnCount = 8

    val grid            = buildGrid(rowCount, columnCount)
    val spreadSheetView = new SpreadsheetView(grid)

    val filter  = new FilterBase(spreadSheetView, 0)
    val filter1 = new FilterBase(spreadSheetView, 1)
    val filter2 = new FilterBase(spreadSheetView, 2)
    val filter3 = new FilterBase(spreadSheetView, 3)
    val filter4 = new FilterBase(spreadSheetView, 4)

    // Use import 'trick' to reduce need to prefix calls will 'spreadSheetView'
    import spreadSheetView._
    setFilteredRow(11)
    getColumns.get(0).setFilter(filter)
    getColumns.get(1).setFilter(filter1)
    getColumns.get(2).setFilter(filter2)
    getColumns.get(3).setFilter(filter3)
    getColumns.get(4).setFilter(filter4)

    spreadSheetView.getFixedRows.add(10)

    //Hiding rows.
    val picker = new Picker() {
      override def onClick(): Unit = { //If my details are hidden
        if (getHiddenRows.get(3)) {
          getStyleClass.remove("plus-picker")
          getStyleClass.add("minus-picker")
          showRow(3)
          showRow(4)
          showRow(5)
          showRow(6)
        }
        else {
          getStyleClass.remove("minus-picker")
          getStyleClass.add("plus-picker")
          hideRow(3)
          hideRow(4)
          hideRow(5)
          hideRow(6)
        }
      }
    }
    picker.getStyleClass.setAll("plus-picker")
    getRowPickers.put(2, picker)
    hideRow(3)
    hideRow(4)
    hideRow(5)
    hideRow(6)
    getStylesheets.add(this.getClass.getResource("spreadsheetSample2.css").toExternalForm)

    spreadSheetView
  }

  private def buildGrid(rowCount: Int, columnCount: Int): GridBase = {
    val grid     = new GridBase(rowCount, columnCount)
    val rows     = new ObservableBuffer[ObservableBuffer[SpreadsheetCell]]()
    var rowIndex = 0
    rows.add(getSeparator(grid, rowIndex))
    rowIndex += 1
    rows.add(getTitle(grid, rowIndex))
    rowIndex += 1
    rows.add(getSubTitle(grid, rowIndex))
    rowIndex += 1
    rows.add(getSeparator(grid, rowIndex))
    rowIndex += 1
    rows.add(getContact1(grid, rowIndex))
    rowIndex += 1
    rows.add(getContact2(grid, rowIndex))
    rowIndex += 1
    rows.add(getContact3(grid, rowIndex))
    rowIndex += 1
    rows.add(getSeparator(grid, rowIndex))
    rowIndex += 1
    rows.add(getOrderTitle(grid, rowIndex))
    rowIndex += 1
    rows.add(getClickMe(grid, rowIndex))
    rowIndex += 1
    rows.add(getSeparator(grid, rowIndex))
    rowIndex += 1
    rows.add(getHeader(grid, rowIndex))
    rowIndex += 1

    for (i <- rowIndex until rowIndex + 100) {
      val randomRow = ObservableBuffer.empty[SpreadsheetCell]
      randomRow.add(SpreadsheetCellType.INTEGER.createCell(i, 0, 1, 1, (Math.random * 100).toInt))
      randomRow.add(SpreadsheetCellType.INTEGER.createCell(i, 1, 1, 1, i))
      randomRow.add(SpreadsheetCellType.INTEGER.createCell(i, 2, 1, 1, (Math.random * 100).toInt))
      val cell = SpreadsheetCellType.DOUBLE.createCell(i, 3, 1, 1, Math.random * 100)
      cell.setFormat("##.##")
      randomRow.add(cell)
      randomRow.add(SpreadsheetCellType.INTEGER.createCell(i, 4, 1, 1, (Math.random * 2).toInt))
      for (column <- 5 until grid.getColumnCount) {
        randomRow.add(SpreadsheetCellType.STRING.createCell(i, column, 1, 1, ""))
      }
      rows.add(randomRow)
    }
    // Map to JavaFX `delegate` to provide type expected by Grid
    grid.setRows(rows.map(_.delegate))
    grid.spanColumn(4, 1, 0)
    grid.spanColumn(4, 2, 0)
    grid.spanColumn(4, 8, 0)

    grid
  }

  private def getSeparator(grid: GridBase, row: Int): ObservableBuffer[SpreadsheetCell] = {
    val separator = ObservableBuffer.empty[SpreadsheetCell]
    for (column <- 0 until grid.getColumnCount) {
      val cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "")
      cell.setEditable(false)
      cell.getStyleClass.add("separator")
      separator.add(cell)
    }
    separator
  }

  private def getTitle(grid: GridBase, row: Int) = {
    val title = ObservableBuffer.empty[SpreadsheetCell]
    var cell  = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, "Customer order details")
    cell.setEditable(false)
    cell.getStyleClass.add("title")
    title.add(cell)
    for (column <- 1 until grid.getColumnCount) {
      cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "")
      cell.setEditable(false)
      title.add(cell)
    }
    title
  }

  private def getSubTitle(grid: GridBase, row: Int) = {
    val title = ObservableBuffer.empty[SpreadsheetCell]
    var cell  = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, "Customer details")
    cell.setEditable(false)
    cell.getStyleClass.add("subtitle")
    title.add(cell)
    for (column <- 1 until grid.getColumnCount) {
      cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "")
      cell.setEditable(false)
      title.add(cell)
    }
    title
  }

  private def getContact1(grid: GridBase, row: Int) = {
    val title = ObservableBuffer.empty[SpreadsheetCell]
    var cell  = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, "Number")
    cell.setEditable(false)
    cell.getStyleClass.add("customer")
    title.add(cell)
    title.add(SpreadsheetCellType.STRING.createCell(row, 1, 1, 1, "156"))
    for (column <- 2 until grid.getColumnCount) {
      cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "")
      cell.setEditable(false)
      title.add(cell)
    }
    title
  }

  private def getContact2(grid: GridBase, row: Int) = {
    val title = ObservableBuffer.empty[SpreadsheetCell]
    var cell  = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, "Customer name")
    cell.setEditable(false)
    cell.getStyleClass.add("customer")
    title.add(cell)
    title.add(SpreadsheetCellType.STRING.createCell(row, 1, 1, 1, "Samir"))
    for (column <- 2 until grid.getColumnCount) {
      cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "")
      cell.setEditable(false)
      title.add(cell)
    }
    title
  }

  private def getContact3(grid: GridBase, row: Int) = {
    val title = ObservableBuffer.empty[SpreadsheetCell]
    var cell  = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, "City")
    cell.setEditable(false)
    cell.getStyleClass.add("customer")
    title.add(cell)
    title.add(SpreadsheetCellType.STRING.createCell(row, 1, 1, 1, "Paris"))
    cell = SpreadsheetCellType.STRING.createCell(row, 2, 1, 1, "")
    //    cell.setGraphic(new ImageView(new Image(classOf[Utils].getResourceAsStream("frenchFlag.png"))))
    cell.setEditable(false)
    title.add(cell)
    for (column <- 3 until grid.getColumnCount) {
      cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "")
      cell.setEditable(false)
      title.add(cell)
    }
    title
  }

  private def getOrderTitle(grid: GridBase, row: Int) = {
    val title = ObservableBuffer.empty[SpreadsheetCell]
    var cell  = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, "Order details")
    cell.setEditable(false)
    cell.getStyleClass.add("subtitle")
    title.add(cell)
    for (column <- 1 until grid.getColumnCount) {
      cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "")
      cell.setEditable(false)
      title.add(cell)
    }
    title
  }

  private def getClickMe(grid: GridBase, row: Int) = {
    val title = ObservableBuffer.empty[SpreadsheetCell]
    var cell  = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, "Click me!")
    val item  = new MenuItem("Go to ControlsFX")
    item.onAction = (_) => {
      val desktop = if (Desktop.isDesktopSupported) Desktop.getDesktop
      else null
      try desktop.browse(new URI("http://fxexperience.com/controlsfx/"))
      catch {
        case ex@(_: IOException | _: URISyntaxException) =>
          ex.printStackTrace()
        //                        Logger.getLogger(HelloSpreadsheetView2.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    cell.getPopupItems.add(item)
    cell.setHasPopup(true)
    cell.setEditable(false)
    title.add(cell)
    for (column <- 1 until grid.getColumnCount) {
      cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "")
      cell.setEditable(false)
      title.add(cell)
    }
    title
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
    val rowHeaderCheckBox        = new CheckBox()
    val columnHeaderCheckBox     = new CheckBox()
    val selectionModeCheckBox    = new CheckBox()
    val displaySelectionCheckBox = new CheckBox()
    val editableCheckBox         = new CheckBox()


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
    rowHeaderCheckBox.selected = true
    spreadSheetView.setShowRowHeader(true)

    grid.add(rowHeaderCheckBox, 1, row)
    row += 1

    rowHeaderCheckBox.selected.onChange((_, _, newVal) => spreadSheetView.setShowRowHeader(newVal))

    // column header
    val columnHeaderLabel = new Label {
      text = "Column header: "
      styleClass += "property"
    }
    grid.add(columnHeaderLabel, 0, row)
    columnHeaderCheckBox.selected = true
    spreadSheetView.setShowColumnHeader(true)
    grid.add(columnHeaderCheckBox, 1, row)
    row += 1

    columnHeaderCheckBox.selected.onChange((_, _, newVal) => spreadSheetView.setShowColumnHeader(newVal))

    // editable
    val editableLabel = new Label {
      text = "Editable: "
      styleClass += "property"
    }
    grid.add(editableLabel, 0, row)
    editableCheckBox.selected = true
    spreadSheetView.setEditable(true)
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
    selectionModeCheckBox.selected = true
    grid.add(selectionModeCheckBox, 1, row)
    row += 1

    selectionModeCheckBox.selected.onChange { (_, _, isSelected) =>
      spreadSheetView.getSelectionModel.clearSelection()
      spreadSheetView.getSelectionModel.setSelectionMode(
        if (isSelected) SelectionMode.Multiple else SelectionMode.Single)
    }

    // Display selection
    val displaySelectionLabel = new Label {
      text = "Display selection: "
      styleClass.add("property")
    }
    grid.add(displaySelectionLabel, 0, row)
    displaySelectionCheckBox.selected = true
    grid.add(displaySelectionCheckBox, 1, row)
    row += 1

    displaySelectionCheckBox.selected.onChange { (_, _, isSelected) =>
      spreadSheetView.getGrid.setDisplaySelection(isSelected)
      spreadSheetView.getSelectionModel.clearSelection()
    }
    grid
  }
}
