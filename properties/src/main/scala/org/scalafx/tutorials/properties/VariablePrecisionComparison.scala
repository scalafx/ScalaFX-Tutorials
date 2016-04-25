package org.scalafx.tutorials.properties

import scalafx.Includes._
import scalafx.beans.property.DoubleProperty

/**
  * Boolean expression that compares a double property to double value within given precision.
  */
object VariablePrecisionComparison extends App {

  val a = DoubleProperty(1)
  val b = a === 2d +- 0.1d

  println(s"Setting a to ${a.value}:  b = ${b.value}")

  a() = 1.5
  println(s"Setting a to ${a.value}:  b = ${b.value}")

  a() = 1.95
  println(s"Setting a to ${a.value}:  b = ${b.value}")

  a() = 2.5
  println(s"Setting a to ${a.value}:  b = ${b.value}")

}

