package cell_factories

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.ListView
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

object ListViewCustomDemo extends JFXApp3 {

  case class Person(firstName: String, lastName: String)

  override def start(): Unit = {
    case class Person(firstName: String, lastName: String, color: Color)
    val characters = ObservableBuffer[Person](
      Person("Bungalow ", "Bill", Color.DodgerBlue),
      Person("Dennis", "O'Dell", Color.Brown),
      Person("Eleanor", "Rigby", Color.Olive),
      Person("Rocky", "Raccoon", Color.DarkCyan),
      Person("Peggy", "Sue", Color.Coral)
      )
    stage = new PrimaryStage {
      title = "ListView with Selection Demo"
      scene = new Scene {
        content = new ListView[Person] {
          items = characters

          cellFactory = (cell, value) => {
            cell.text = s"${value.firstName} ${value.lastName}"
            cell.graphic = new Circle {
              fill = value.color
              radius = 4
            }
          }
        }
      }
    }
  }
}
