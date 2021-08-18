package com.example.repositories

import com.example.app.{CustomUser, User}
import com.example.models.Models.{users, Users}
import slick.jdbc.H2Profile.api._
import scala.concurrent.Future

class UserRepositoryImpl(db: Database) extends UserRepository {
  def all: Future[Seq[User]] = db.run(users.result)

  def save(newCustomUser: User): Unit = ???
  def update(updatedCustomUser: User): Unit = ???
  def getByLastName(nameOfInterest: String): Future[Seq[User]] = db.run(users.filter(_.lastName === nameOfInterest).result)
  def getById(id: Int): Future[Seq[User]] = db.run(users.filter(_.id === id).result)
}
