package co.com.application.controllers.queries

import co.com.application.controllers.commands.Dependency
import co.com.infrastructure.acl.formats.Formats._
import co.com.infrastructure.acl.http.HTTPError
import co.com.libs.akka.interop.zio.AkkaHttpEnhancement._
import org.slf4j
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{ Action, AnyContent, MessagesAbstractController, MessagesControllerComponents }

import javax.inject.Inject

class FindUsersQuery @Inject() ( dependency: Dependency, cc: MessagesControllerComponents )
  extends MessagesAbstractController( cc ) with HTTPError {

  val logger: slf4j.Logger = Logger( getClass ).logger

  def find(): Action[AnyContent] = Action.zio { implicit request =>

    logger.info( "find all users" )
    dependency.userRepo.findAll().provide( dependency.dbReadOnly )
      .fold( { error =>
        logger.error( s"an error was occurred: $error.toString()}." )
        badRequest( error )
      }, user => {
        Ok( Json.toJson( user ) )
      } )
  }

}
