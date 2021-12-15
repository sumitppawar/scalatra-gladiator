package com.example.repositories

import com.typesafe.config.ConfigFactory
import com.example.app.{CustomUser, User}
import com.example.models.Models
// import slick.jdbc.JdbcBackend.Database
import scala.concurrent.Future
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

class UserRepositoryImpl(protected val dbConf: DatabaseConfig[JdbcProfile], dbProfile: String, models: Models) extends UserRepository {

  import dbConf.profile.api._
  val db = Database.forConfig(dbProfile, ConfigFactory.load("application"))

  def all: Future[Seq[User]] = db.run(models.users.result)

  def save(newCustomUser: User): Unit = ???
  def update(updatedCustomUser: User): Unit = ???
  def getByLastName(nameOfInterest: String): Future[Seq[User]] = db.run(models.users.filter(_.lastName === nameOfInterest).result)
  def getById(id: Int): Future[Option[User]] = db.run(models.users.filter(_.id === id).result.headOption)
  
}
