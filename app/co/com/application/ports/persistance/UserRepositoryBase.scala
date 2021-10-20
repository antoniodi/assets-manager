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

trait UserRepositoryBase {

  def add( user: User, validFrom: LocalDateTime ): Reader[DatabaseConfig[JdbcProfile], EitherTResult[Done]]

  def addZio( user: User, validFrom: LocalDateTime ): ZIO[DatabaseConfig[JdbcProfile], AppError, Done]

  def findWithTask( username: String ): Reader[DatabaseConfig[JdbcProfile], EitherTResult[Option[User]]]

  def findWithFuture( username: String )( implicit ec: ExecutionContext ): Reader[DatabaseConfig[JdbcProfile], EitherFResult[Option[User]]]

  def findWithZio( username: String ): ZIO[DatabaseConfig[JdbcProfile], AppError, Option[User]]

  def findAll(): ZIO[DatabaseConfig[JdbcProfile], AppError, List[User]]

  def generateId(): String

}
