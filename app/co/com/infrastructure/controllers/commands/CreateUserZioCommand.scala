package co.com.infrastructure.controllers.commands

import co.com.infrastructure.acl.dtos.UserDTO
import co.com.infrastructure.acl.formats.Formats.{ doneWrite, userDTOReads }
import co.com.infrastructure.acl.http.ErrorHandler.handleHttpError
import co.com.libs.command.core.{ Command, CommandHelper }
import play.api.http.ContentTypes
import play.api.libs.json.{ JsPath, Json, Reads }
import play.api.mvc.{ Result, Results }
import zio.{ URIO, ZIO }

case class CreateUserZioCommand( userDTO: UserDTO ) extends Command[Dependency] with Results with ContentTypes {

  def execute: URIO[Dependency, Result] = {
    ZIO.accessZIO[Dependency]( _.savePersistenceUserService.saveUser( userDTO ) )
      .fold( {
        handleHttpError
      }, { result =>
        Ok( Json.toJson( result ) ).as( JSON )
      } )
  }
}

class CreateUserZioCommandHelper extends CommandHelper[Dependency] {
  def name: String = "create-user"

  def commandReads: Reads[Command[Dependency]] = JsPath.read[UserDTO].map( CreateUserZioCommand.apply )
}