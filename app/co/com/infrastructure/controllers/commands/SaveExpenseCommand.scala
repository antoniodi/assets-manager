package co.com.infrastructure.controllers.commands

import co.com.infrastructure.acl.dtos.ExpenseRequestDto
import co.com.infrastructure.acl.formats.Formats.TransactionFormat.expenseFormat
import co.com.infrastructure.acl.http.ErrorHandler.handleHttpError
import co.com.infrastructure.config.Dependency
import co.com.libs.command.core.{ Command, CommandHelper }
import play.api.http.ContentTypes
import play.api.libs.json.{ JsPath, Json, Reads }
import play.api.mvc.{ Result, Results }
import zio.{ IO, URIO, ZIO }

import java.util.UUID

case class SaveExpenseCommand( dto: ExpenseRequestDto ) extends Command[Dependency] with Results with ContentTypes {

  def execute: URIO[Dependency, Result] = {
    val expense = for {
      dependency <- ZIO.environment[Dependency]
      relatedAsset <- dto.assetId match {
        case Some( id ) => dependency.assetRepo.find( id ).provide( dependency.dbConfig )
        case None       => IO.succeed( None )
      }
      relatedLiability <- dto.assetId match {
        case Some( id ) => dependency.liabilityRepo.find( id ).provide( dependency.dbConfig )
        case None       => IO.succeed( None )
      }
      uid = UUID.randomUUID().toString
      validExpense <- dependency.expenseRequestDtoTransformer.toExpense( uid, dto, relatedAsset, relatedLiability )
      currentDate = dependency.dateHelper.getNow
      _ <- dependency.expenseRepo.save( validExpense, currentDate ).provide( dependency.dbConfig )
    } yield uid

    expense
      .fold( {
        handleHttpError
      }, { result =>
        Created( Json.toJson( result ) ).as( JSON )
      } )
  }
}

class SaveExpensesCommandHelper extends CommandHelper[Dependency] {
  def name: String = "save-expense"

  def commandReads: Reads[Command[Dependency]] = JsPath.read[ExpenseRequestDto].map( SaveExpenseCommand.apply )
}