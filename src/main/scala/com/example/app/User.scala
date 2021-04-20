package com.example.app

import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

case class User(firstName: String, lastName: String, id: Integer)

object User {

  def toString(in: User): String = in.asJson.noSpaces

}

