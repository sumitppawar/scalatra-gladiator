package com.example.models

import slick.jdbc.H2Profile.api._
import com.example.app.{User, CustomUser}
import scala.io.Source

object Models {

  class Users(tag: Tag) extends Table[User](tag, "User")
  {
    def firstName: Rep[String] = column[String]("FIRST_NAME")
    def lastName: Rep[String] = column[String]("LAST_NAME")
    def id: Rep[Int] = column[Int]("ID")
    def dob: Rep[String] = column[String]("DOB")

    def * = (firstName, lastName, id, dob) <> (User.tupled, User.unapply)

  }

  val initialUserDataset = Source.fromResource("UsersDataset.csv").getLines().map(_.split(",")).
    map(x => User(x(0).trim(), x(1).trim(), x(2).trim().toInt, x(3).trim())).toList

  def users: TableQuery[Users] = TableQuery[Users]

  val setup = DBIO.seq(
    users.schema.create,
    users ++=initialUserDataset
  )

}
