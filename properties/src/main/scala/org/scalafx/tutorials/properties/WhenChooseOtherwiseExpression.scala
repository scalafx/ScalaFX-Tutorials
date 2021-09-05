package org.scalafx.tutorials.properties

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

/**
 * Example use of `when/choose/otherwise` binding expression..
 */
object WhenChooseOtherwiseExpression extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title.value = "when/choose/otherwise"
      width = 400
      height = 300
      scene = new Scene {
        fill = Color.LightGreen
        content = new Rectangle {
          x = 25
          y = 40
          width = 100
          height = 100
          fill <== when(hover) choose Color.Green otherwise Color.Red
        }
      }
    }
  }
}
