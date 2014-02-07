package event_filters

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.beans.property.BooleanProperty
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control._
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout._
import scalafx.scene.{Scene, Group, Node}


/**
 * Example of using mouse event filters.
 * Most important part is in `makeDraggable(Node)` method.
 *
 * Based on example from JavaFX tutorial [[http://docs.oracle.com/javafx/2/events/filters.htm Handling JavaFX Events]].
 */
object DraggablePanelsExample2 extends JFXApp {

  // TODO use Subscription to add/remove event filter

  private val dragModeActiveProperty = new BooleanProperty(this, "dragModeActive", true)
  private val borderStyle = "" +
    "-fx-background-color: white;" +
    "-fx-border-color: black;" +
    "-fx-border-width: 1;" +
    "-fx-border-radius: 6;" +
    "-fx-padding: 6;"

  stage = new JFXApp.PrimaryStage() {

    val panelsPane = new Pane() {
      val loginPanel = makeDraggable(createLoginPanel())
      val confirmationPanel = makeDraggable(createConfirmationPanel())
      val progressPanel = makeDraggable(createProgressPanel())

      loginPanel.relocate(0, 0)
      confirmationPanel.relocate(0, 67)
      progressPanel.relocate(0, 106)

      content = Seq(loginPanel, confirmationPanel, progressPanel)
      alignmentInParent = Pos.TOP_LEFT
    }

    val dragModeCheckbox = new CheckBox("Drag mode") {
      margin = Insets(6)
      selected = dragModeActiveProperty()
    }

    dragModeActiveProperty <== dragModeCheckbox.selected

    title = "Draggable Panels Example"
    scene = new Scene(400, 300) {
      root = new BorderPane() {
        center = panelsPane
        bottom = dragModeCheckbox
      }
    }
  }

  private def makeDraggable(node: Node): Node = {

    val dragContext = new DragContext()

    new Group(node) {
      filterEvent(MouseEvent.Any) {
        (me: MouseEvent) =>
          if (dragModeActiveProperty()) {
            me.eventType match {
              case MouseEvent.MousePressed =>
                dragContext.mouseAnchorX = me.x
                dragContext.mouseAnchorY = me.y
                dragContext.initialTranslateX = node.translateX()
                dragContext.initialTranslateY = node.translateY()
              case MouseEvent.MouseDragged =>
                node.translateX = dragContext.initialTranslateX + me.x - dragContext.mouseAnchorX
                node.translateY = dragContext.initialTranslateY + me.y - dragContext.mouseAnchorY
              case _ =>
            }
            me.consume()
          }
      }
    }
  }

  private def createLoginPanel(): Node = {
    val toggleGroup1 = new ToggleGroup()

    val textField = new TextField() {
      prefColumnCount = 10
      promptText = "Your name"
    }

    val passwordField = new PasswordField() {
      prefColumnCount = 10
      promptText = "Your password"
    }

    val choiceBox = new ChoiceBox[String](
      ObservableBuffer(
        "English", "\u0420\u0443\u0441\u0441\u043a\u0438\u0439",
        "Fran\u00E7ais")) {
      tooltip = Tooltip("Your language")
      selectionModel().select(0)
    }

    new HBox(6) {
      content = Seq(
        new VBox(2) {
          content = Seq(
            new RadioButton("High") {
              toggleGroup = toggleGroup1
              selected = true
            },
            new RadioButton("Medium") {
              toggleGroup = toggleGroup1
              selected = false
            },
            new RadioButton("Low") {
              toggleGroup = toggleGroup1
              selected = false
            }
          )
        },
        new VBox(2) {
          content = Seq(textField, passwordField)
        },
        choiceBox
      )

      alignment = Pos.BOTTOM_LEFT
      style = borderStyle
    }
  }

  private def createConfirmationPanel(): Node = new HBox(6) {
    val acceptanceLabel = new Label("Not Available")
    content = Seq(
      new Button("Accept") {
        onAction = handle {acceptanceLabel.text = "Accepted"}
      },
      new Button("Decline") {
        onAction = handle {acceptanceLabel.text = "Declined"}
      },
      acceptanceLabel
    )
    alignment = Pos.CENTER_LEFT
    style = borderStyle
  }

  private def createProgressPanel(): Node = new HBox(6) {
    val slider = new Slider()
    val progressIndicator = new ProgressIndicator() {
      progress <== slider.value / slider.max
    }
    content = Seq(new Label("Progress:"), slider, progressIndicator)
    style = borderStyle
  }

  private final class DragContext {
    var mouseAnchorX: Double = 0d
    var mouseAnchorY: Double = 0d
    var initialTranslateX: Double = 0d
    var initialTranslateY: Double = 0d
  }

}
