package co.com.application.acl.formats

import co.com.application.acl.dtos.{ BankDTO, PostDTO, UserDTO }
import co.com.domain.model.entities.User
import co.com.suite.error.ApplicationError
import play.api.libs.json._

object Formats {

  implicit val applicationErrorFormat: Writes[ApplicationError] =
    ( error: ApplicationError ) => JsObject( Map( "errorMessage" -> JsString( error.errorMessage ) ) )

  implicit val userDTOWrite: Writes[User] = Json.writes[User]
  implicit val userDTOReads: Reads[UserDTO] = Json.reads[UserDTO]
  implicit val postDTOReads: OFormat[PostDTO] = Json.format[PostDTO]
  implicit val bankDTOWrite: Writes[BankDTO] = Json.writes[BankDTO]

}
