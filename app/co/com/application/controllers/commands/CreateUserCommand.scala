package co.com.application.controllers.commands

import cats.data.NonEmptyList
import co.com.application.acl.dtos.UserDTO
import co.com.application.acl.formats.Formats._
import co.com.application.acl.http.HTTPError
import co.com.suite.error.{ DetailServiceError, SaveError }
import play.api.libs.json.Json
import play.api.mvc.{ MessagesAbstractController, MessagesControllerComponents }

import javax.inject.Inject
import scala.concurrent.{ ExecutionContext, Future }

class CreateUserCommand @Inject() (
    dependency: Dependency,
    cc: MessagesControllerComponents )( implicit ec: ExecutionContext )
  extends MessagesAbstractController( cc ) with HTTPError {

  implicit val sc = commandScheduler

  def create = Action.async { request =>
    request.body.asJson.fold {
      Future.successful( badRequest( NonEmptyList.of( DetailServiceError( s"For request ${request.toString} [Invalid Json]" ) ) ) )
    }( json => json.validate[UserDTO].fold( { errors =>
      Future.successful( badRequest( NonEmptyList.of( DetailServiceError( s"For request ${request.toString} [Invalid Json: ${errors.toList.mkString( ", " )}]" ) ) ) )
    }, { userDTO =>

      dependency.savePersistenceUserService.saveUser( userDTO ).run( dependency ).fold( {
        internalServerError( _ )
      }, { value =>
        Ok( Json.toJson( value.toString ) )
      } ).runToFuture
    } ) )
  }

}
