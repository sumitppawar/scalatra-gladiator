package com.example.app

import java.sql.Date
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

case class UserCustom (firstName: String, lastName: String, dob: Date) {
  override def toString: String = this.asJson.noSpaces
}

object UserCustom {

}