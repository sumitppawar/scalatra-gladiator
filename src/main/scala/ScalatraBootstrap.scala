import com.example.app._
import com.typesafe.config.ConfigFactory
import org.scalatra._
import javax.servlet.ServletContext
import org.slf4j.{Logger, LoggerFactory}
import slick.jdbc.H2Profile.api._
import com.example.models.Models.setup
import scala.util._
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

class ScalatraBootstrap extends LifeCycle {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  override def init(context: ServletContext) {

    try {
      val db = Database.forConfig("h2mem1", ConfigFactory.load("application"))

      val setupFuture = db.run(setup)
      Await.result(setupFuture, 2 minutes)

      context.mount(new UserController(db, logger), "/*")
    } catch {
      case e: Throwable => {
        logger.error(e.toString)
        logger.error(e.getMessage)
        logger.error(e.getStackTrace.toString)
      }
    } finally {
      destroy(context)
    }

  }

  override def destroy(context: ServletContext): Unit = super.destroy(context)

}
