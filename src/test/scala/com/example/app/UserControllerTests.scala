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

class UserControllerTests extends ScalatraFunSuite {

  val db = Database.forConfig("h2mem1", ConfigFactory.load("application"))
  Await.result(db.run(setup), 2 minutes)

  addServlet(
    new UserController(db),
    "/*"
  )

  val responseCodeOk = 200

  test("GET / on MyScalatraServlet should return status 200") {
    get("/") {
      status should equal (responseCodeOk)
    }
  }

  test("GET /getarandomuser on UserController should return status 200") {
    get("/getarandomuser") {
      status should equal(responseCodeOk)
    }
  }

  // TODO: Ask about where/how body & status variables are defined/come from?
  test("GET /getarandomuser on UserController using the test dataset returns the first user with ID 1") {
    get("/getarandomuser"){
      decode[User](body) match {
        case Right(y) => assert(y.id == 1)
        case _ => false
      }
    }
  }

  test("GET /getallusers on UserController should return a valid Response") {
    get("/getallusers") {
      status should equal(responseCodeOk)
      // body should equal ("x")
      // var bodyContent = Await.result(body, Duration(500, MILLISECONDS))
    }
  }

  test("GET /gerruserbyid/1 on UserControllerTests Servlet should return status 200") {
    get("/getruserbyid/1") {
      status should equal (responseCodeOk)
      assert(body == "x")
    }
  }

  // import scala.concurrent.Await
  // import scala.concurrent.duration._


  // override def header = ???
}
