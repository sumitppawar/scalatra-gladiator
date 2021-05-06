package com.example.repositories

import com.example.app.{CustomUser, User}
import com.example.models.Models.users
import slick.jdbc.H2Profile.api._
import scala.concurrent.Future

class UserRepositoryImpl(db: Database) extends UserRepository {
  def all: Future[Seq[User]] = db.run(users.result)
  def getByLastName(f: (String, String) => Boolean): Future[Seq[User]] = ???
  def save(newCustomUser: User): Unit = ???
  def update(updatedCustomUser: User): Unit = ???
}
