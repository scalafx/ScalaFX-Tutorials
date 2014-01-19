package sfxml

import scalafx.event.ActionEvent
import scalafx.scene.control.{TextArea, ChoiceBox, TextField}
import scalafx.scene.layout.GridPane
import scalafxml.core.macros.sfxml

@sfxml
class AdoptionFormPresenter(private val sizeTextField: TextField,
                            private val breedTextField: TextField,
                            private val sexChoiceBox: ChoiceBox[String],
                            private val additionalInfoTextArea: TextArea,
                            private val grid: GridPane) {

  def handleSubmit(event: ActionEvent) {
    grid.gridLinesVisible() = !grid.gridLinesVisible()
  }

  def handleClear(event: ActionEvent) {
    sizeTextField.text = ""
    breedTextField.text = ""
    sexChoiceBox.selectionModel().clearSelection()
    additionalInfoTextArea.text = ""
  }
}
