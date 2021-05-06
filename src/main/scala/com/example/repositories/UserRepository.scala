package com.example.repositories

import com.example.app.{CustomUser, User}

import scala.concurrent.Future

trait UserRepository {
  def all: Future[Seq[User]]
  def getByLastName(f: (String, String) => Boolean): Future[Seq[User]]
  def save(newCustomUser: User): Unit
  def update(updatedCustomUser: User): Unit
}
