package org.scalafx.slick_table

import scalafx.beans.property.StringProperty

/**
  */
case class Person(id: Option[Int], first: String, last: String, email: String) {
  val firstName = new StringProperty(this, "firstName", first)
  val lastName = new StringProperty(this, "lastName", last)
  val emailAddress = new StringProperty(this, "emailAddress", email)
}


