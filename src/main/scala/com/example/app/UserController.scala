package com.example.app

import org.scalatra._

class UserController extends ScalatraServlet {

  get("/user/1") {
    val json =
      """
        |{
        | "id": 1
        |}
        |""".stripMargin
    Ok(json)
  }
}
