package co.com.libs.command.core

import co.com.libs.akka.interop.zio.AkkaHttpEnhancement._
import co.com.libs.error.AppError
import play.api.mvc._
import zio.IO

import javax.inject.Inject

abstract class CommandHandler @Inject() ( dependency: DependencyBase, cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) {

  def commandHelperList: List[CommandHelper]

  def executeCommand( commandName: String ): Action[AnyContent] = {
    commandHelperList.find( _.name == commandName ) match {
      case Some( command ) => execute( command )
      case None            => Action { Results.BadRequest( s"The command $commandName doesn't exist." ) }
    }
  }

  def execute( commandHelper: CommandHelper ): Action[AnyContent] = Action.zio { request =>
    request.body.asJson.fold {
      IO.fromEither[AppError, Result]( Right( Results.BadRequest( "Invalid Json" ) ) )
    } {
      _.validate[Command]( commandHelper.commandReads ).fold( { invalid =>
        IO.fromEither[AppError, Result]( Right( Results.BadRequest( s"For request ${request.toString()} [Invalid Json: ${invalid.toString()}]" ) ) )
      }, { command =>
        command.execute.provide( dependency )
      } )
    }
  }

}
