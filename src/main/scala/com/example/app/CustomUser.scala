package com.example.app

import io.circe.Decoder.Result

import java.util.Date
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

case class CustomUser(firstName: String, lastName: String, dob: Date)

object CustomUser {

  implicit val encodeUserCustom: Encoder[CustomUser] = new Encoder[CustomUser] {
    override def apply(a: CustomUser): Json = Json.obj(
      ("firstName", Json.fromString(a.firstName)),
      ("lastName", Json.fromString(a.lastName)),
      ("dob", Json.fromString(a.dob.toString))
    )
  }

  /*implicit val dateDecoder: Decoder[Date] = new Decoder[Date] {
    override def apply(c: HCursor): Result[Date] =
  }*/

  implicit val decodeUserCustom: Decoder[CustomUser] = new Decoder[CustomUser] {
    final def apply(c: HCursor): Decoder.Result[CustomUser] =
      for {
        firstName <- c.downField("firstName").as[String]
        lastName <- c.downField("lastName").as[String]
        dob <- c.downField("dob").as[String]
      } yield {
        new CustomUser(firstName, lastName, stringToDate(dob))
      }
  }

  private def stringToDate(in: String): Date = {

    import java.text.DateFormat
    import java.text.SimpleDateFormat

    val formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa")

    // TODO: Option Lift the parse() function
    val date = formatter.parse(in)
    date

  }

}