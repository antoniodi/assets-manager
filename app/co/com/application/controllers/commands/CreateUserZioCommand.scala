package co.com.application.controllers.commands

import co.com.infrastructure.Types.ZIOS
import co.com.infrastructure.acl.dtos.UserDTO
import co.com.infrastructure.acl.formats.Formats.userDTOReads
import co.com.libs.command.core.{ Command, CommandHelper, DependencyBase }
import play.api.http.ContentTypes
import play.api.libs.json.{ JsPath, Reads }
import play.api.mvc.{ Result, Results }
import zio.ZIO

case class CreateUserZioCommand( userDTO: UserDTO ) extends Command with Results with ContentTypes {

  def execute = {
    ZIO.accessZIO[Dependency]( _.savePersistenceUserService.saveUser( userDTO ) )
      .map( _ => Ok( s"""{\"resultado\": \"OK\"}""" ).as( JSON ) )
      .asInstanceOf[ZIOS[DependencyBase, Result]]
  }
}

class CreateUserZioCommandHelper extends CommandHelper {
  def name: String = "create-user"

  def commandReads: Reads[Command] = JsPath.read[UserDTO].map( CreateUserZioCommand.apply )
}