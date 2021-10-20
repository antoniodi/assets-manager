package co.com.libs.command.core

import co.com.libs.akka.interop.zio.AkkaHttpEnhancement._
import play.api.libs.json.{ JsError, JsSuccess, JsValue }
import play.api.mvc._
import zio.UIO

import javax.inject.Inject

abstract class CommandHandler[D <: DependencyBase] @Inject() ( dependency: D, cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) {

  def commandHelperList: List[CommandHelper[D]]

  def executeCommand( commandName: String ): Action[JsValue] = {
    commandHelperList.find( _.name == commandName ) match {
      case Some( command ) => execute( command )
      case None            => Action( parse.json ) { _ => BadRequest( s"The command $commandName doesn't exist." ) }
    }
  }

  def execute( commandHelper: CommandHelper[D] ): Action[JsValue] = Action.zio( parse.json ) { request =>
    request.body.validate[Command[D]]( commandHelper.commandReads ).fold( { invalid =>
      UIO.succeed( BadRequest( s"For request ${request.toString()} [Invalid Json: ${invalid.toString()}]" ) )
    }, { command =>
      command.execute.provide( dependency )
    } )
  }

}
