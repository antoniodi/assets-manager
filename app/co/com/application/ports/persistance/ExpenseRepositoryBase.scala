package co.com.application.ports.persistance

import akka.Done
import co.com.domain.model.entities.Expense
import co.com.libs.error.AppError
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import zio.ZIO

import java.time.LocalDateTime

trait ExpenseRepositoryBase {

  def findAll: ZIO[DatabaseConfig[JdbcProfile], AppError, List[Expense]]

  def save( expense: Expense, date: LocalDateTime ): ZIO[DatabaseConfig[JdbcProfile], AppError, Done]

}
