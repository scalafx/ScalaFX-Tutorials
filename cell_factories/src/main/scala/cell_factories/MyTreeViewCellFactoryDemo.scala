package cell_factories

import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control._

/**
 * https://groups.google.com/forum/#!topic/scalafx-users/jDuMETUx3fY
 */
object MyTreeViewCellFactoryDemo extends JFXApp3 {

  case class Person(firstName: String, lastName: String, children: List[Person] = Nil)

  def toTreeItem(p: Person): TreeItem[Person] = {
    if (p.children.isEmpty) new TreeItem(p)
    else new TreeItem(p) {
      children = p.children map toTreeItem
    }
  }

  override def start(): Unit = {
    val children1 = List(
      Person("Bungalow", "Bill"),
      Person("Dennis", "O’Dell"),
      Person("Peggy", "Sue"),
      Person("Molly", "Jones")
      )

    val children2 = List(
      Person("Maxwell", "Edison"),
      Person("Desmond", "Jones"),
      Person("Loretta", "Martin")
      )

    val parents = ObservableBuffer[Person](
      Person("Eleanor", "Rigby", children1),
      Person("Rocky", "Raccoon", children2)
      )

    stage = new JFXApp3.PrimaryStage {
      title = "TreeView CellFactory Demo"
      scene = new Scene {
        content = new TreeView[Person] {
          prefWidth = 250
          prefHeight = 250
          showRoot = false
          root = new TreeItem[Person] {
            expanded = true
            children = parents.map(toTreeItem).toSeq
          }
          cellFactory = (cell, value) => {
            cell.text = value.firstName + " " + value.lastName
          }
        }
      }
    }
  }
}
