package com.example.repositories

import scala.concurrent.Future
import com.example.app.CustomUser

trait CustomUserRepository {
  def all: Future[Seq[CustomUser]]
  def getByLastName(f: (String, String) => Boolean): Future[Seq[CustomUser]]
  def save(newCustomUser: CustomUser): Unit
  def update(updatedCustomUser: CustomUser): Unit
}
