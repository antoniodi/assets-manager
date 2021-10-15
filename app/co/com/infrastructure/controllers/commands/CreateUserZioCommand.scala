package co.com.infrastructure.controllers.commands

import co.com.infrastructure.acl.dtos.UserDTO
import co.com.infrastructure.acl.formats.Formats.userDTOReads
import co.com.infrastructure.acl.http.HTTPError
import co.com.libs.command.core.{ Command, CommandHelper }
import play.api.http.ContentTypes
import play.api.libs.json.{ JsPath, Reads }
import play.api.mvc.{ Result, Results }
import zio.{ URIO, ZIO }

case class CreateUserZioCommand( userDTO: UserDTO ) extends Command[Dependency] with Results with ContentTypes with HTTPError {

  def execute: URIO[Dependency, Result] = {
    ZIO.accessZIO[Dependency]( _.savePersistenceUserService.saveUser( userDTO ) )
      .fold( {
        internalServerError
      }, { _ =>
        Ok( s"""{\"resultado\": \"OK\"}""" ).as( JSON )
      } )
  }
}

class CreateUserZioCommandHelper extends CommandHelper[Dependency] {
  def name: String = "create-user"

  def commandReads: Reads[Command[Dependency]] = JsPath.read[UserDTO].map( CreateUserZioCommand.apply )
}