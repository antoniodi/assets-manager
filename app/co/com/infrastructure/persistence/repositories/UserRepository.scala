package co.com.infrastructure.persistence.repositories

import akka.Done
import cats.data.Reader
import co.com.application.ports.persistance.UserRepositoryBase
import co.com.domain.model.entities.User
import co.com.infrastructure.Types.{ EitherFResult, EitherTResult }
import co.com.infrastructure.controllers.queries.queryExecutionContext
import co.com.infrastructure.persistence.generarUUID
import co.com.infrastructure.persistence.tables.users
import co.com.infrastructure.persistence.transformers.UserTransformer.{ userRowToUser, userToUserRow }
import co.com.libs.error.{ AppError, DataBaseError }
import co.com.libs.slick.interop.zio.SlickEnhancement._
import org.slf4j
import play.api.Logger
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import zio.ZIO

import java.time.LocalDateTime
import scala.concurrent.{ ExecutionContext, ExecutionContextExecutorService }

object UserRepository extends UserRepositoryBase {

  val logger: slf4j.Logger = Logger( getClass ).logger

  implicit val ec: ExecutionContextExecutorService = queryExecutionContext

  def addZio( user: User, validFrom: LocalDateTime ): ZIO[DatabaseConfig[JdbcProfile], AppError, Done] = {
    val insert = users += userToUserRow( user, validFrom )
    ZIO.fromDBIO( insert )
      .map( _ => Done )
      .mapError( error => {
        val errorMessage = s"An error occurred trying to save the users with username: ${user.username}."
        logger.error( errorMessage, error )
        DataBaseError( errorMessage )
      } )
  }

  def findWithZio( username: String ): ZIO[DatabaseConfig[JdbcProfile], AppError, Option[User]] = {
    val query = users.filter( _.username === username )
    ZIO.fromDBIO( query.result )
      .map( _.headOption.map( userRowToUser ) )
      .refineOrDie( error => {
        val errorMessage = s"An error occurred trying to find the user with username: $username."
        logger.error( errorMessage, error )
        DataBaseError( errorMessage )
      } )
  }

  def findAll(): ZIO[DatabaseConfig[JdbcProfile], AppError, List[User]] = {
    ZIO.fromDBIO( users.result )
      .map( _.map( userRowToUser ).toList )
      .refineOrDie( error => {
        val errorMessage = s"An error occurred trying to find all users."
        logger.error( errorMessage, error )
        DataBaseError( errorMessage )
      } )
  }

  def generateId(): String = {
    s"u-$generarUUID"
  }

  def add( user: User, validFrom: LocalDateTime ): Reader[DatabaseConfig[JdbcProfile], EitherTResult[Done]] = ???

  def findWithTask( username: String ): Reader[DatabaseConfig[JdbcProfile], EitherTResult[Option[User]]] = ???

  def findWithFuture( username: String )( implicit ec: ExecutionContext ): Reader[DatabaseConfig[JdbcProfile], EitherFResult[Option[User]]] = ???
}
