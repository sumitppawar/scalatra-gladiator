package com.example.app

import com.example.app.User.toString
import org.scalatra._

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

  get("/userx/:id") {
    val u: User = User("Shrinivas", "Deshmukh", params("id").toInt)

    Ok(u.toStringX)
  }
}
