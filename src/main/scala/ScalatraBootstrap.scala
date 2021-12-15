import com.typesafe.config.ConfigFactory
import com.example.app._
import org.scalatra._
import javax.servlet.ServletContext
import org.slf4j.{Logger, LoggerFactory}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import com.example.models.Models
import scala.util._
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

class ScalatraBootstrap extends LifeCycle {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  override def init(context: ServletContext) {

    try {
      val dbConf = DatabaseConfig.forConfig[JdbcProfile]("devdb") // Load the DB Configuration 
      import dbConf.profile.api._

      val db = Database.forConfig("devdb", ConfigFactory.load("application"))
      val models = new Models(dbConf)
      val setupFuture = db.run(models.setup)
      val dbProfile = "devdb"
      
      Await.result(setupFuture, 2 minutes)

      context.mount(new UserController(dbConf, logger, dbProfile, models), "/*")

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
