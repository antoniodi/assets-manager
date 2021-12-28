package co.com.infrastructure.controllers.commands

import co.com.infrastructure.acl.dtos.ExpenseRequestDto
import co.com.infrastructure.acl.formats.Formats.TransactionFormat.expenseFormat
import co.com.infrastructure.acl.http.ErrorHandler.handleHttpError
import co.com.infrastructure.config.Dependency
import co.com.libs.command.core.{ Command, CommandHelper }
import co.com.libs.error.BusinessError
import play.api.http.ContentTypes
import play.api.libs.json.{ JsPath, Json, Reads }
import play.api.mvc.{ Result, Results }
import zio.{ IO, URIO }

import java.util.UUID

case class SaveExpenseCommand( dto: ExpenseRequestDto ) extends Command[Dependency] with Results with ContentTypes {

  def execute: URIO[Dependency, Result] = {
    val uid = UUID.randomUUID().toString
    fakeSave( uid )
      .fold( {
        handleHttpError
      }, { result =>
        Created( Json.toJson( result ) ).as( JSON )
      } )
  }
  def fakeSave( uid: String ): IO[BusinessError, String] = {
    if ( 1 == 1 ) IO.succeed( uid ) else IO.fail( BusinessError( "Hola " ) )
  }
}

class SaveExpensesCommandHelper extends CommandHelper[Dependency] {
  def name: String = "save-expense"

  def commandReads: Reads[Command[Dependency]] = JsPath.read[ExpenseRequestDto].map( SaveExpenseCommand.apply )
}