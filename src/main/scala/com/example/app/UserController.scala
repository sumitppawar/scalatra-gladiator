package com.example.app

import org.scalatra._

import java.util.Date
import io.circe.{Error, _}
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._


class UserController extends ScalatraServlet {

  get("/userx/1") {
    val json =
      """
        |{
        | "id": 1
        |}
        |""".stripMargin
    Ok(json)
  }

  get("/user/:id") {
    val u: User = User("Sumit", "Pawar", params("id").toInt)

    Ok(User.toString(u))
  }

  get("/customuser") {
    val x: CustomUser = CustomUser("Shrinivas", "Deshmukh", new Date())

    Ok(x.asJson.noSpaces, headers=Map("Content-Type" -> "application/json"))
  }

  post("/customuser") {

    decode[CustomUser](request.body) match {
      case Left(a) => BadRequest(s"Invalid definition, error encountered ${a}")
      case Right(b) => Ok(b.asJson.noSpaces)
    }

  }

}
