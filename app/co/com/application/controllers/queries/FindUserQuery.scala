package co.com.application.controllers.queries

import co.com.application.acl.formats.Formats._
import co.com.application.acl.http.HTTPError
import co.com.application.controllers.commands.Dependency
import org.slf4j
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{ Action, AnyContent, MessagesAbstractController, MessagesControllerComponents }

import javax.inject.Inject
import scala.concurrent.ExecutionContextExecutorService

class FindUserQuery @Inject() (
    dependency: Dependency,
    cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) with HTTPError {

  implicit val ec: ExecutionContextExecutorService = queryExecutionContext

  val logger: slf4j.Logger = Logger( getClass ).logger

  def findUser( username: String ): Action[AnyContent] = Action.async { implicit request =>

    logger.info( "find users" )
    dependency.userRepo.findWithFuture( username ).run( dependency.dbReadOnly )
      .fold( { errors =>
        logger.error( s"an error was occurred: ${errors.toString()}." )
        internalServerError( errors )
      }, user => {
        logger.info( s"user was found: ${user.toString}." )
        Ok( Json.toJson( user ) )
      } )
  }

}
