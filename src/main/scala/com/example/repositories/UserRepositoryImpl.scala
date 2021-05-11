package com.example.repositories

import com.example.app.{CustomUser, User}
import com.example.models.Models.users
import slick.jdbc.H2Profile.api._
import scala.concurrent.Future

class UserRepositoryImpl(db: Database) extends UserRepository {
  def all: Future[Seq[User]] = db.run(users.result)

  def save(newCustomUser: User): Unit = ???
  def update(updatedCustomUser: User): Unit = ???

  // Should i do this given that the String var is lifted to Rep[String]
  def getByLastName(f: (String) => Boolean): Future[Seq[User]] = ??? // users.filter(f(_.lastName))

  def getByLastName(nameOfInterest: String): Future[Seq[User]] = db.run(users.filter(_.lastName === nameOfInterest).result)
  def getById(id: Int): Future[Seq[User]] = db.run(users.filter(_.id === id).result)
}
