package com.example.app

import org.scalatra._
import java.util.Date
import scala.util._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import com.example.repositories.UserRepositoryImpl
import slick.jdbc.H2Profile.api._
import UserImplicits.{toString => userToString, _}
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps
import org.slf4j.{Logger}


class UserController(db: Database, logger: Logger) extends ScalatraServlet with FutureSupport {

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
    Ok(
      Await.result(u.all, 5 minutes).asJson, 
      headers=Map("Content-Type" -> "application/json")
    )
  }

  get("/getuserbyid/:id") {
    import UserImplicits._
    Await.result(u.getById(params("id").toInt), 5 minutes) match {
      case Some(x: User) => Ok(x.asJson)
      case _ => BadRequest(s"User ID ${params("id")} not found")
    }
  }

  get("/getusersoflastname/:lastName") {
    import UserImplicits._
    val res = Await.result(u.getByLastName(params("lastName")), 5 minutes)
    res match {
      case Seq() => BadRequest(s"Users with the last name specified do not exists")
      case Seq(_*) => Ok(res.asJson)
    }
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
