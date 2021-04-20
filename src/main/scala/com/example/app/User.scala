package com.example.app

import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

case class User(firstName: String, lastName: String, id: Integer) {
  def toStringX: String = this.asJson.noSpaces
}

object User {
  // Q. anyway to use toString as an instance/class method?
  def toString(in: User): String = in.asJson.noSpaces

}

