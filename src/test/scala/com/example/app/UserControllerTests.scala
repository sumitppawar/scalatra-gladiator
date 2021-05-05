package com.example.app

import org.scalatra.test.scalatest._
import org.scalatra._

class UserControllerTests extends ScalatraFunSuite {

  addServlet(classOf[UserController], "/*")

  // val responseCodeOk = 200

  test("GET / on MyScalatraServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

  test("GET /userx/1 on UserControllerTests Servlet should return status 200") {
    get("/userx/1") {
      status should equal (200)
    }
  }

}
