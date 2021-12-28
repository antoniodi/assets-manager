package co.com.infrastructure.persistence.repositories

import akka.Done
import co.com.application.ports.persistance.ExpenseRepositoryBase
import co.com.domain.model.entities.User
import co.com.libs.error.AppError
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import zio.ZIO

import java.time.LocalDateTime

object ExpenseRepository extends ExpenseRepositoryBase {
  def save( user: User, validFrom: LocalDateTime ): ZIO[DatabaseConfig[JdbcProfile], AppError, Done] = ???

  def generateId(): String = ???
}
