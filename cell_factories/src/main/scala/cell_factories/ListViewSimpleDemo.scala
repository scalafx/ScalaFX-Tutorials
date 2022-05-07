package cell_factories

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.ListView

object ListViewSimpleDemo extends JFXApp3 {

  case class Person(firstName: String, lastName: String)

  override def start(): Unit = {
    case class Person(firstName: String, lastName: String)
    val characters = ObservableBuffer[Person](
      Person("Bungalow ", "Bill"),
      Person("Dennis", "O\u2019Dell"),
      Person("Eleanor", "Rigby"),
      Person("Rocky", "Raccoon"),
      Person("Peggy", "Sue")
      )
    stage = new PrimaryStage {
      title = "ListView with Selection Demo"
      scene = new Scene {
        content = new ListView[Person] {
          items = characters
        }
      }
    }
  }
}
