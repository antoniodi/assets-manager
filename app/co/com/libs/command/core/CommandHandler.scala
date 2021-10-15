package co.com.libs.command.core

import co.com.libs.akka.interop.zio.AkkaHttpEnhancement._
import play.api.mvc._
import zio.UIO

import javax.inject.Inject

abstract class CommandHandler[D <: DependencyBase] @Inject() ( dependency: D, cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) {

  def commandHelperList: List[CommandHelper[D]]

  def executeCommand( commandName: String ): Action[AnyContent] = {
    commandHelperList.find( _.name == commandName ) match {
      case Some( command ) => execute( command )
      case None            => Action { Results.BadRequest( s"The command $commandName doesn't exist." ) }
    }
  }

  def execute( commandHelper: CommandHelper[D] ): Action[AnyContent] = Action.zio { request =>
    request.body.asJson.fold {
      UIO.succeed( Results.BadRequest( "Invalid Json" ) )
    } {
      _.validate[Command[D]]( commandHelper.commandReads ).fold( { invalid =>
        UIO.succeed( Results.BadRequest( s"For request ${request.toString()} [Invalid Json: ${invalid.toString()}]" ) )
      }, { command =>
        command.execute.provide( dependency )
      } )
    }
  }

}
