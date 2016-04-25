package org.scalafx.tutorials.properties

import scalafx.beans.property.DoubleProperty

/**
  * Property binding examples.
  */
object PropertyBinding extends App {

  val a = DoubleProperty(1)
  val b = DoubleProperty(2)
  val c = DoubleProperty(3)
  val d = DoubleProperty(4)

  println(s"a = $a, b= $b, c = $c, d = d")

  a <== b
  c <==> d

  println(s"a = ${a()}, b= ${b()}, c = ${c()}, d = ${d()}")

  b() = 5
  println(s"a = ${a()}, b= ${b()}, c = ${c()}, d = ${d()}")

  c() = 7
  println(s"a = ${a()}, b= ${b()}, c = ${c()}, d = ${d()}")

  d() = 9
  println(s"a = ${a()}, b= ${b()}, c = ${c()}, d = ${d()}")

}
