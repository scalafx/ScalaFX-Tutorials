/*
 * Copyright (c) 2011-2014, ScalaFX Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ScalaFX Project nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE SCALAFX PROJECT OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package cell_factories

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control._

/** Illustrates use of TreeView CellFactory to do custom rendering of a TreeCell. */
object TreeViewCellFactoryDemo extends JFXApp {

  trait PersonNode

  case class Branch(label: String) extends PersonNode

  case class Person(firstName: String, lastName: String) extends PersonNode

  val characters = ObservableBuffer[Person](
    Person("Bungalow", "Bill"),
    Person("Maxwell", "Edison"),
    Person("Desmond", "Jones"),
    Person("Molly", "Jones"),
    Person("Dennis", "O’Dell"),
    Person("Eleanor", "Rigby"),
    Person("Rocky", "Raccoon"),
    Person("Peggy", "Sue")
  )

  val groups = characters.groupBy(_.lastName.head).toSeq.sortBy(_._1)

  val rootItem = new TreeItem[PersonNode]()
  groups.foreach { case (initial, members) =>
    val branch = new TreeItem[PersonNode](Branch(initial.toString))
    members.foreach { p => branch.children += new TreeItem[PersonNode](p) }
    rootItem.children += branch
  }


  stage = new PrimaryStage {
    title = "TreeView CellFactory Demo"
    scene = new Scene {
      root = new TreeView[PersonNode] {
        prefWidth = 200
        prefHeight = 150
        showRoot = false
        root = rootItem
        //        // Use CellFactory to do custom rendering of a TreeCell,
        //        // deriving from JavaFX `TreeCell` let us use custom `updateItem`.
        //        cellFactory = _ =>
        //          new javafx.scene.control.TreeCell[PersonNode] {
        //
        //            // We are deriving from JavaFX, for easier use add ScalaFX wrapper for  `this`
        //            val self: TreeCell[PersonNode] = this
        //
        //            override def updateItem(item: PersonNode, empty: Boolean): Unit = {
        //              super.updateItem(item, empty)
        //              self.graphic = null
        //              self.text =
        //                if (empty) null
        //                else item match {
        //                  case p: Person => p.firstName + " " + p.lastName
        //                  case b: Branch => b.label
        //                  case _ => "???"
        //                }
        //            }
        //          }
        cellFactory = { _ =>
          new TreeCell[PersonNode] {
            item.onChange { (_, _, newValue) =>
              graphic = null
              text = newValue match {
                case p: Person => p.firstName + " " + p.lastName
                case b: Branch => b.label
                case null => null
                case _ => "???"
              }
            }
          }
        }
      }
    }
  }
}
