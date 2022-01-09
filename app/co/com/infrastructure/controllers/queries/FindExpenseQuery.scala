package co.com.infrastructure.controllers.queries

import co.com.infrastructure.acl.formats.Formats.TransactionFormat.writesExpenses
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

  def findAll(): Action[AnyContent] = Action.zio { implicit request =>
    logger.info( "find all expenses" )
    dependency.expenseRepo.findAll.provide( dependency.dbReadOnly )
      .fold( {
        handleHttpError
      }, expenses => {
        Ok( Json.toJson( expenses ) )
      } )
  }

}
