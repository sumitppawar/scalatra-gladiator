package com.example.app

import org.scalatra._
import java.util.Date
import scala.util._
import io.circe.parser._
import io.circe.syntax._
import com.example.repositories.UserRepositoryImpl
import slick.jdbc.H2Profile.api._
import UserImplicits.{toString => userToString, _}
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps


class UserController(db: Database) extends ScalatraServlet with FutureSupport {

  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global
  val u = new UserRepositoryImpl(db)

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

  get("/getarandomuser") {
    Ok(
      Await.result(u.all, 2 minutes).head.asJson.noSpaces,
      headers=Map("Content-Type" -> "application/json")
    )
  }

  get("/getallusers") {
    Ok(Await.result(u.all, 5 minutes).map(_.toString), 
      headers=Map("Content-Type" -> "application/json")
    )
  }

  get("/getruserbyid/:id") {
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
