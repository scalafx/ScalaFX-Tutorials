package cell_factories

import scalafx.Includes._
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
          //        // Use CellFactory to do custom rendering of a TreeCell
          //        cellFactory = (v: TreeView[Person]) => new TreeCell[Person] {
          //          treeItem.onChange { (_, _, p) =>
          //            text = if (p != null) p.value().firstName + " " + p.value().lastName else null
          //            graphic = null
          //          }
          //        }
          // Use CellFactory to do custom rendering of a TreeCell,
          // deriving from JavaFX `TreeCell` let us use custom `updateItem`.
          // There can be some ghost artifact if `updateItem` is not properly uses
          // and handled situations when item is marked `empty`
          cellFactory = _ =>
            new javafx.scene.control.TreeCell[Person] {

              // We are deriving from JavaFX, for easier use add ScalaFX wrapper for  `this`
              val self: TreeCell[Person] = this

              override def updateItem(item: Person, empty: Boolean): Unit = {
                super.updateItem(item, empty)
                self.graphic = null
                self.text =
                  if (empty) null
                  else item match {
                    case p: Person => p.firstName + " " + p.lastName
                    case null => null
                  }
              }
            }

        }
      }
    }
  }
}
