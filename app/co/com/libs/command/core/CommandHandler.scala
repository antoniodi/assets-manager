package co.com.libs.command.core

import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.Future

abstract class CommandHandler @Inject() ( dependency: DependencyBase, cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) {

  def commandHelperList: List[CommandHelper]

  def executeCommand( commandName: String ): Action[AnyContent] = {
    commandHelperList.find( _.name == commandName ) match {
      case Some( command ) => execute( command )
      case None            => Action { Results.BadRequest( s"The command $commandName doesn't exist." ) }
    }
  }

  def execute( commandHelper: CommandHelper ): Action[AnyContent] = Action.async { request =>
    request.body.asJson.fold {
      Future.successful( Results.BadRequest( "Invalid Json" ) )
    } { _.validate[Command]( commandHelper.commandReads ).fold( { invalid =>
        Future.successful( Results.BadRequest( s"For request ${request.toString()} [Invalid Json: ${invalid.toString()}]" ) )
      }, { command =>
        command.execute.run( dependency )
      } )
    }
  }

}
