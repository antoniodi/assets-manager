package co.com.infrastructure.persistence.repositories

import akka.Done
import co.com.application.ports.persistance.ExpenseRepositoryBase
import co.com.domain.model.entities.Expense
import co.com.infrastructure.persistence.tables.{ ExpenseRow, expenses }
import co.com.infrastructure.persistence.transformers.ExpenseTransformer.toExpense
import co.com.libs.error.{ AppError, DataBaseError }
import co.com.libs.slick.interop.zio.SlickEnhancement._
import co.com.libs.zio.enhancement.ZioEnhancement.ZIOOps
import org.slf4j
import play.api.Logger
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import zio.ZIO

import java.sql.Timestamp
import java.time.LocalDateTime

object ExpenseRepository extends ExpenseRepositoryBase {

  val logger: slf4j.Logger = Logger( getClass ).logger

  def save( expense: Expense, date: LocalDateTime ): ZIO[DatabaseConfig[JdbcProfile], AppError, Done] = {
    val insert = expenses += ExpenseRow( expense.id, Timestamp.valueOf( date ), expense.category.code, expense.description, expense.value.currency.code, expense.value.amount.toDouble, expense.asset.map( _.id ), expense.liability.map( _.id ) )
    ZIO.fromDBIO( insert )
      .map( _ => Done )
      .mapError( error => {
        val errorMessage = s"An error occurred trying to save the expense: [${expense.description}]."
        logger.error( errorMessage, error )
        DataBaseError( errorMessage )
      } )
  }

  def findAll: ZIO[DatabaseConfig[JdbcProfile], AppError, List[Expense]] = {
    ZIO.fromDBIO( expenses.result )
      .refineOrDie( error => {
        val errorMessage = s"An error occurred trying to find the expenses."
        logger.error( errorMessage, error )
        DataBaseError( errorMessage )
      } )
      .flatMap( expenses => ZIO.sequence( expenses.map( toExpense ).toList ) )
  }
}
