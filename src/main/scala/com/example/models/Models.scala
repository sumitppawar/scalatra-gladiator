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

    // override def * = (firstName, lastName, id, dob) <> (User.tupled, User.unapply)
  }

  val initialUserDataset = Source.fromResource("UsersDataset.csv").getLines().map(_.split(",")).
    map(x => User(x(0), x(1), Integer.parseInt(x(2)), x(3))).toList

  def users: TableQuery[Users] = TableQuery[Users]

  val setup = DBIO.seq(
    users.schema.create,
    users ++= initialUserDataset
  )

}
