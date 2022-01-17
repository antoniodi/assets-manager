import com.typesafe.scalalogging.StrictLogging
import io.gatling.app.Gatling
import io.gatling.core.ConfigKeys.core

import java.io.File
import scala.collection.mutable
import scala.util.{Failure, Success, Try}

object Main extends StrictLogging {

  def main(args: Array[String]): Unit = {
    args match {
      case Array( "-all" ) =>
        val simulationsFolder = "test-classes/simulations/"
        new File( simulationsFolder).list()
          .map( fileName => s"simulations.${fileName.replace(".class", "")}" )
          .foldLeft( () )( ( _, simulationClassName ) => {
            val simulationArguments = mutable.Map( core.SimulationClass -> simulationClassName )
            Try( Gatling.fromMap( simulationArguments ) ) match {
              case Failure( exception ) => logger.error(s"The execution of the simulation class: [$simulationClassName] failed.", exception )
              case Success( statusCode ) => logger.info( s"The execution finished with status code: s[$statusCode]." )
            }
          } )
      case _ => Gatling.main(args)
    }
  }

}
