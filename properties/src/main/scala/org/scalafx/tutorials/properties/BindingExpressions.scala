package org.scalafx.tutorials.properties

import scalafx.beans.property.DoubleProperty

/**
  * Examples of numeric binding expressions.
  */
object BindingExpressions extends App {
  val base = DoubleProperty(15)
  val height = DoubleProperty(10)
  val area = DoubleProperty(0)

  area <== base * height / 2

  printValues()

  println("Setting base to " + 20)
  base() = 20

  printValues()

  println("Setting height to " + 5)
  height() = 5

  printValues()

  def printValues(): Unit = {
    println(f"base = ${base()}%4.1f, height = ${height()}%4.1f, area = ${area()}%5.1f\n")
  }
}
