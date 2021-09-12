package co.com.application.controllers.queries

import cats.effect.{ ContextShift, IO }
import co.com.application.acl.http.HTTPError
import co.com.application.controllers.commands.Dependency
import play.api.libs.json.Json
import play.api.mvc.{ MessagesAbstractController, MessagesControllerComponents }
import co.com.application.acl.formats.Formats.bankDTOWrite

import javax.inject.Inject
import scala.concurrent.ExecutionContextExecutorService

class BankQuery @Inject() (
    dependency: Dependency,
    cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) with HTTPError {

  implicit val ec: ExecutionContextExecutorService = queryExecutionContext

  implicit val cs: ContextShift[IO] = IO.contextShift( ec )

  def findBanks( username: String ) = Action.async { implicit request =>
    //    dependency.requestBanksService.getBanks().runAsync()
    //      .fold( internalServerError, bank => Ok( Json.toJson( bank ) ) )
    ???
  }

}