package com.example.app

import org.scalatra._
import java.util.Date
import scala.io.Source
import io.circe.parser._
import io.circe.syntax._
import com.example.repositories.UserRepositoryImpl
import slick.jdbc.H2Profile.api._
import UserImplicits.{toString => userToString, _}


class UserController(db: Database) extends ScalatraServlet with FutureSupport {

  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

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
    val u: User = User("Sumit", "Pawar", params("id").toInt, "2021-01-01")

    Ok(userToString(u))
  }

  get("/getallusers") {
    val u = new UserRepositoryImpl(db)
    Ok(u.all)
  }

  get("/gerruserbyid/:id") {
    val u = new UserRepositoryImpl(db)
    Ok(u.getById(params("id").toInt))
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
