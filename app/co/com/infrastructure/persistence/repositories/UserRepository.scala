package co.com.infrastructure.persistence.repositories

import akka.Done
import cats.data.Reader
import co.com.application.contracts.UserRepositoryBase
import co.com.application.controllers.queries.queryExecutionContext
import co.com.domain.model.entities.User
import co.com.infrastructure.Types.{ EitherFResult, EitherTResult, ZIOS }
import co.com.infrastructure.persistence.generarUUID
import co.com.infrastructure.persistence.tables.users
import co.com.infrastructure.persistence.transformers.UserTransformer.{ userRowToUser, userToUserRow }
import co.com.libs.error.InfrastructureError
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

  /**
   * Implementation with Task of Monix (Monix library)
   * Reduce boilerplate generated by future implementation because you
   * always need an implicit variable execution context to run an action
   */
  def add( user: User, validFrom: LocalDateTime ): Reader[DatabaseConfig[JdbcProfile], EitherTResult[Done]] = Reader {
    //    dbConfig: DatabaseConfig[JdbcProfile] =>
    //
    //      val query = users += userToUserRow( user, validFrom )
    //
    //      EitherT {
    //        Task.deferFuture( dbConfig.db.run( query ) )
    //          .map( _ => Right( Done ) )
    //          .onErrorRecover {
    //            case error: Throwable =>
    //              logger.error( s"${SaveError( "user" ).errorMessage}.", error )
    //              Left( NonEmptyList.of( SaveError( "user" ), TransactionError( error.getMessage ) ) )
    //          }
    //      }
    ???
  }

  def addZio( user: User, validFrom: LocalDateTime ): ZIOS[DatabaseConfig[JdbcProfile], Done] = {
    val insert = users += userToUserRow( user, validFrom )
    ZIO.fromDBIO( insert )
      .map( _ => Done )
      .mapError( error => {
        val errorMessage = s"An error occurred trying to save the users with username: ${user.username}."
        logger.error( errorMessage, error )
        InfrastructureError( errorMessage )
      } )
  }

  def findWithTask( username: String ): Reader[DatabaseConfig[JdbcProfile], EitherTResult[Option[User]]] = Reader {
    //    dbConfig: DatabaseConfig[JdbcProfile] =>
    //      val query = users.filter( _.username === username )
    //      EitherT {
    //        Task.deferFuture( dbConfig.db.run( query.result ) )
    //          .map( result => Right( result.headOption.map( userRowToUser ) ) )
    //          .onErrorRecover {
    //            case error: Throwable =>
    //              logger.error( FindError( "user", username ).errorMessage, error )
    //              Left( NonEmptyList.of( FindError( "user", username ), TransactionError( error.getMessage ) ) )
    //          }
    //      }
    ???
  }

  def findWithZio( username: String ): ZIOS[DatabaseConfig[JdbcProfile], Option[User]] = {
    val query = users.filter( _.username === username )
    ZIO.fromDBIO( query.result )
      .map( _.headOption.map( userRowToUser ) )
      .refineOrDie( error => {
        val errorMessage = s"An error occurred trying to find the user with username: $username."
        logger.error( errorMessage, error )
        InfrastructureError( errorMessage )
      } )
  }

  def findAll(): ZIOS[DatabaseConfig[JdbcProfile], List[User]] = {
    ZIO.fromDBIO( users.result )
      .map( _.map( userRowToUser ).toList )
      .refineOrDie( error => {
        val errorMessage = s"An error occurred trying to find all users."
        logger.error( errorMessage, error )
        InfrastructureError( errorMessage )
      } )
  }

  /**
   * Implementation with Scala concurrent future You can pass
   * the execution context in the method or define the execution context as a class variable
   */
  def findWithFuture( username: String )( implicit ec: ExecutionContext ): Reader[DatabaseConfig[JdbcProfile], EitherFResult[Option[User]]] = Reader {
    //    dbConfig: DatabaseConfig[JdbcProfile] =>
    //
    //      val query = users.filter( _.username === username )
    //
    //      EitherT {
    //        dbConfig.db.run( query.result )
    //          .map( result => Right( result.headOption.map( userRowToUser ) ) )
    //          .recover {
    //            case error: Throwable =>
    //              logger.error( FindError( "user", username ).errorMessage, error )
    //              Left( NonEmptyList.of( FindError( "user", username ), TransactionError( error.getMessage ) ) )
    //          }
    //      }
    ???
  }

  def generateId(): String = {
    s"u-$generarUUID"
  }

}
