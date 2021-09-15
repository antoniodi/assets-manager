package co.com.application.controllers.commands

import cats.data.Reader
import co.com.infrastructure.acl.dtos.UserDTO
import co.com.infrastructure.acl.formats.Formats.userDTOReads
import co.com.infrastructure.acl.http.HTTPError
import co.com.libs.command.core.{ Command, CommandHelper, DependencyBase }
import play.api.http.ContentTypes
import play.api.libs.json.{ JsPath, Reads }
import play.api.mvc.{ Result, Results }
import zio.UIO

case class CreateUserZioCommand( userDTO: UserDTO ) extends Command with Results with ContentTypes with HTTPError {

  def execute: Reader[DependencyBase, UIO[Result]] = Reader {
    case dependency: Dependency =>
      dependency.savePersistenceUserService.saveUser( userDTO ).provide( dependency )
        .fold( {
          internalServerError
        }, { _ =>
          Ok( s"""{\"resultado\": \"OK\"}""" ).as( JSON )
        } )
  }
}

class CreateUserZioCommandHelper extends CommandHelper {
  def name: String = "create-user"

  def commandReads: Reads[Command] = JsPath.read[UserDTO].map( CreateUserZioCommand.apply )
}