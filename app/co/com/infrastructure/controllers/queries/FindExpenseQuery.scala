package co.com.infrastructure.controllers.queries

import co.com.infrastructure.acl.formats.Formats.TransactionFormat.writesExpense
import co.com.infrastructure.acl.http.ErrorHandler.handleHttpError
import co.com.infrastructure.config.Dependency
import co.com.libs.akka.interop.zio.AkkaHttpEnhancement._
import org.slf4j
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{ Action, AnyContent, MessagesAbstractController, MessagesControllerComponents }

import javax.inject.Inject

class FindExpenseQuery @Inject() ( dependency: Dependency, cc: MessagesControllerComponents )
  extends MessagesAbstractController( cc ) {

  val logger: slf4j.Logger = Logger( getClass ).logger

  def find( id: String ): Action[AnyContent] = Action.zio { implicit request =>
    logger.info( s"find expense by id: [$id]" )
    dependency.expenseRepo.find( id ).provide( dependency.dbReadOnly )
      .fold( {
        handleHttpError
      }, optionExpense => {
        optionExpense.fold( NoContent )( expense => Ok( Json.toJson( expense ) ) )
      } )
  }

}
