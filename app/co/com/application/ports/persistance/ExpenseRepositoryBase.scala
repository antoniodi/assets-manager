package co.com.application.ports.persistance

import akka.Done
import cats.data.Reader
import co.com.domain.model.entities.User
import co.com.infrastructure.Types.{ EitherFResult, EitherTResult }
import co.com.libs.error.AppError
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import zio.ZIO

import java.time.LocalDateTime
import scala.concurrent.ExecutionContext

trait ExpenseRepositoryBase {

  def save( user: User, validFrom: LocalDateTime ): ZIO[DatabaseConfig[JdbcProfile], AppError, Done]

  def generateId(): String

}
