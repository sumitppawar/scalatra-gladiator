import com.example.app._
import com.typesafe.config.ConfigFactory
import org.scalatra._
import javax.servlet.ServletContext
import org.slf4j.{Logger, LoggerFactory}
import slick.jdbc.H2Profile.api._
import com.example.models.Models.setup

class ScalatraBootstrap extends LifeCycle {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  override def init(context: ServletContext) {
    val db = Database.forConfig("h2mem1", ConfigFactory.load("application"))
    context.mount(new UserController(db), "/*")

    val setupFuture = db.run(setup)
  }

  override def destroy(context: ServletContext): Unit = super.destroy(context)

}
