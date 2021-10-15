package co.com.application.ports.persistance

import akka.Done
import cats.data.Reader
import co.com.domain.model.entities.User
import co.com.infrastructure.Types.{ EitherFResult, EitherTResult, ZIOS }
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import java.time.LocalDateTime
import scala.concurrent.ExecutionContext

trait UserRepositoryBase {

  def add( user: User, validFrom: LocalDateTime ): Reader[DatabaseConfig[JdbcProfile], EitherTResult[Done]]

  def addZio( user: User, validFrom: LocalDateTime ): ZIOS[DatabaseConfig[JdbcProfile], Done]

  def findWithTask( username: String ): Reader[DatabaseConfig[JdbcProfile], EitherTResult[Option[User]]]

  def findWithFuture( username: String )( implicit ec: ExecutionContext ): Reader[DatabaseConfig[JdbcProfile], EitherFResult[Option[User]]]

  def findWithZio( username: String ): ZIOS[DatabaseConfig[JdbcProfile], Option[User]]

  def findAll(): ZIOS[DatabaseConfig[JdbcProfile], List[User]]

  def generateId(): String

}
