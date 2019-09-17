package org.scalafx.slick_table

import org.scalafx.slick_table.ContactsDB.Persons
import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ContactsDB {

  /** Definition of the Persons table */
  class Persons(tag: Tag) extends Table[Person](tag, "PERSONS") {
    def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    def first: Rep[String] = column[String]("FIRST")
    def last: Rep[String] = column[String]("LAST")
    def email: Rep[String] = column[String]("EMAIL")
    def * : ProvenShape[Person] = (id.?, first, last, email) <> (Person.tupled, Person.unapply)
  }

}

/**
  * Wrapper to simplify use of Slick API. It is the domain model for this application.
  */
class ContactsDB {

  private val persons: TableQuery[Persons] = TableQuery[Persons]

  private val samplePersons = Seq(
    Person(None, "Bungalow ", "Bill", "saxon@firce.not"),
    Person(None, "Dennis", "O’Dell", "lutn@sluggers.net"),
    Person(None, "Eleanor", "Rigby", "e.rigby@lonely.com"),
    Person(None, "Rocky", "Raccoon", "nancy@black_mining_hills.dak"),
    Person(None, "Peggy", "Sue", "sue.p@gerron.so")
  )

  /** Setup the contacts database and create the Persons table */
  def setup(): Unit = {
    run(persons.schema.create)
  }

  /** Add sample content of the Persons table */
  def addSampleContent(): Unit = {
    add(samplePersons)
  }

  /** Remove all entries from the Persons table */
  def clear(): Unit = {
    run(persons.delete)
  }

  /** Return current content of the Persons table */
  def queryPersons(): Seq[Person] = {
    run(persons.to[Seq].result)
  }

  /** Add a person to the Persons table */
  def add(p: Person): Unit = {
    add(Seq(p))
  }

  /** Add items to the Persons table */
  def add(items: Seq[Person]): Unit = {
    run(persons ++= items)
  }

  /** Remove items from Persons table */
  def remove(items: Seq[Person]): Unit = {
    val qs = items.map { i =>
      val q = persons.filter { p =>
        p.first === i.first && p.last === i.last && p.email === i.email
      }
      q.delete
    }

    run(DBIO.seq(qs: _*))
  }

  /** Perform database actions and wait for completion. */
  private def run[R](actions: DBIOAction[R, NoStream, Nothing]): R = {
    val db = Database.forConfig("h2mem1")
    try {
      Await.result(db.run(actions), Duration.Inf)
    } finally {
      db.close()
    }
  }
}
