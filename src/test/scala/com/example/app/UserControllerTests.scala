package com.example.app

import org.scalatra.test.scalatest._

class UserControllerTests extends ScalatraFunSuite {

  addServlet(classOf[UserController], "/*")

  test("GET / on MyScalatraServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}
