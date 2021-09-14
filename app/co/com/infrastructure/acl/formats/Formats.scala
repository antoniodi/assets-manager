package co.com.infrastructure.acl.formats

import co.com.domain.model.entities.User
import co.com.infrastructure.acl.dtos.{ BankDTO, PostDTO, UserDTO }
import co.com.libs.error.{ AppError, ApplicationError, BusinessError, InfrastructureError }
import play.api.libs.json._

object Formats {

  //  implicit val applicationErrorFormat: Writes[error.ApplicationError] =
  //    ( error: error.ApplicationError ) => JsObject( Map( "errorMessage" -> JsString( error.message ) ) )

  implicit val appErrorFormat: Writes[AppError] = {
    case businessError: BusinessError             => Json.writes[BusinessError].writes( businessError )
    case applicationError: ApplicationError       => Json.writes[ApplicationError].writes( applicationError )
    case infrastructureError: InfrastructureError => Json.writes[InfrastructureError].writes( infrastructureError )
  }

  //    Json.writes[AppError]

  implicit val userDTOWrite: Writes[User] = Json.writes[User]
  implicit val userDTOReads: Reads[UserDTO] = Json.reads[UserDTO]
  implicit val postDTOReads: OFormat[PostDTO] = Json.format[PostDTO]
  implicit val bankDTOWrite: Writes[BankDTO] = Json.writes[BankDTO]

}
