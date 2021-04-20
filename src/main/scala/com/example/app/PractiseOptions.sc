def add(a: Option[Int], b: Option[Int], c: Option[Int]): Option[Int] = {
  /*for {
    x <- a.getOrElse(0)
    y <- b.getOrElse(0)
    z <- c.getOrElse(0)
  } yield Some(x + y + z)*/

  a.flatMap(Some(_))
}


