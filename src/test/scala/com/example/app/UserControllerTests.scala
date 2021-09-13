package com.example.app

import com.typesafe.config.ConfigFactory
import org.scalatra.test.scalatest._
import org.scalatra._
import com.example.models.Models.setup
import slick.jdbc.H2Profile.api._
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps
import UserImplicits._
import io.circe.parser._
import org.slf4j.{Logger, LoggerFactory}

class UserControllerTests extends ScalatraFunSuite {

  val db = Database.forConfig("h2mem1", ConfigFactory.load("application"))
  Await.result(db.run(setup), 2 minutes)

  val logger: Logger = LoggerFactory.getLogger(getClass)

  addServlet(
    new UserController(db, logger),
    "/*"
  )

  test("GET / on MyScalatraServlet should return status 200") {
    get("/") {
      status should equal (Ok().status)
    }
  }

  test("GET /getarandomuser on UserController using the test dataset returns the first user with ID 1") {
    get("/getarandomuser"){
      status should equal(Ok().status)

      decode[User](body) match {
        case Right(y: User) => assert(y.id == 1)
        case _ => assert(false)
      }
    }
  }

  test("GET /getruserbyid/3 returns a User with the correct ID of 3"){
    get("/getuserbyid/3") {
      var x = body
      decode[User](body) match {
        case Right(y: User) => assert(y.id == 3)
        case _ => assert(false)
      }
    }
  }

  test("GET /getusersoflastname/Brown returns the right single User"){
    get("/getusersoflastname/Brown") {
      status should equal(Ok().status)

      decode[Seq[User]](body) match {
        case Right(Seq(x: User)) => assert(x.lastName == "Brown")
        case _ => assert(false)
      }
    }
  }

  test("GET /getusersoflastname/Walker returns right multiple Users"){
    get("/getusersoflastname/Walker") {
      status should equal(Ok().status)

      decode[Seq[User]](body) match {
        case Right(x: Seq[User]) => {
          assert(x.length == 2)
          assert(x(0).lastName == "Walker")
          assert(x(1).lastName == "Walker")
        }
        case _ => assert(false)
      }
    }
  }

  test("GET /getusersoflastname/NoUser returns a BadRequest as no user of last name NoUser exists"){
    get("/getusersoflastname/NoUser") {
      status should equal(BadRequest().status)
    }
  }

  test("GET /getuserbyid/99 returns a BadRequest as a User of ID 999 does not exist"){
    get("/getuserbyid/999") {
      status should equal(BadRequest().status)
    }
  }

  test("GET /getallusers on UserController should return a valid Response and multiple users that are in the test dataset") {
    get("/getallusers") {
      status should equal(Ok().status)

      decode[Seq[User]](body) match {
        case Right(x: Seq[User]) => {
          assert(x.length > 0)
        }
        case _ => assert(false)
      }
    }
  }

}
