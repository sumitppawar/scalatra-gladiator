package com.example.app

import org.scalatra._
import java.util.Date
import scala.util._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import com.example.repositories.UserRepositoryImpl
import UserImplicits.{toString => userToString, _}
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps
import org.slf4j.{Logger}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import com.typesafe.config.ConfigFactory
import com.example.models.Models


class UserController(protected val dbConf: DatabaseConfig[JdbcProfile],
                      logger: Logger,
                      dbProfile: String,
                      models: Models) extends ScalatraServlet with FutureSupport {

  import dbConf.profile.api._ // Import the JdbcProfile API from the configured profile

  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  val u = new UserRepositoryImpl(dbConf, dbProfile, models)

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

}
