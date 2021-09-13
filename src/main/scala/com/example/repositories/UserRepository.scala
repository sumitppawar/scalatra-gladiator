package com.example.repositories

import com.example.app.{CustomUser, User}

import scala.concurrent.Future

trait UserRepository {
  def all: Future[Seq[User]]
  // def getByLastName(f: (String) => Boolean): Future[Seq[User]]
  def getByLastName(lastName: String): Future[Seq[User]]
  def getById(id: Int): Future[Option[User]]
  def save(newCustomUser: User): Unit
  def update(updatedCustomUser: User): Unit
}
