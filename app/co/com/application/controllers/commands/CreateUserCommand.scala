package co.com.application.controllers.commands

import co.com.infrastructure.acl.dtos.UserDTO
import co.com.infrastructure.acl.formats.Formats._
import co.com.infrastructure.acl.http.HTTPError
import co.com.libs.akka.interop.zio.AkkaHttpEnhancement._
import co.com.libs.error.InfrastructureError
import play.api.libs.json.Json
import play.api.mvc.{ MessagesAbstractController, MessagesControllerComponents }

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class CreateUserCommand @Inject() (
    dependency: Dependency,
    cc: MessagesControllerComponents )( implicit ec: ExecutionContext )
  extends MessagesAbstractController( cc ) with HTTPError {

  implicit val sc = commandScheduler

  def create = Action.zio { request =>
    //    request.body.asJson.fold {
    //      badRequest( InfrastructureError( s"For request ${request.toString} [Invalid Json]" ) )
    //    } {
    //      _.validate[UserDTO].fold( { errors =>
    //        badRequest( InfrastructureError( s"For request ${request.toString} [Invalid Json: ${errors.toList.mkString( ", " )}]" ) )
    //      }, { userDTO =>
    //
    //        dependency.savePersistenceUserService.saveUser( userDTO ).provide( dependency ).fold( {
    //          internalServerError( _ )
    //        }, { value =>
    //          Ok( Json.toJson( value.toString ) )
    //        } )
    //      } )
    //
    //    }
    ???
  }

}
