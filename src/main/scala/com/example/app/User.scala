package com.example.app

import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

case class User(firstName: String, lastName: String, id: Int, dob: String)

object UserImplicits {

  def toString(in: User): String = in.asJson.noSpaces

  implicit val encodeFoo: Encoder[User] = new Encoder[User] {
    final def apply(a: User): Json = Json.obj(
      ("firstName", Json.fromString(a.firstName)),
      ("lastName", Json.fromString(a.lastName)),
      ("id", Json.fromInt(a.id)),
      ("dob", Json.fromString(a.dob))
    )
  }

  implicit val decodeFoo: Decoder[User] = new Decoder[User] {
    final def apply(c: HCursor): Decoder.Result[User] =
      for {
        firstName <- c.downField("firstName").as[String]
        lastName <- c.downField("lastName").as[String]
        id <- c.downField("id").as[Int]
        dob <- c.downField("dob").as[String]
      } yield {
        new User(firstName, lastName, id, dob)
      }
  }

  

}

