package co.com.mock.repositories

import akka.Done
import cats.data.{ EitherT, NonEmptyList, Reader }
import co.com.TestKitBase
import co.com.application.controllers.commands.Dependency
import co.com.domain.model.entities.User
import co.com.suite.error.TransactionError

import java.time.LocalDateTime

trait MockPersistenceUserService extends TestKitBase {

  def mockerFindWithTaskSuccessfully( username: String, result: Option[User] )( implicit dependency: Dependency ) = {
    dependency.userRepo.findWithTask _ expects username returning Reader { _ => EitherT.rightT( result ) }
  }

  def mockerFindWithTaskWithError( implicit dependency: Dependency ) = {
    dependency.userRepo.findWithTask _ expects * returning Reader { _ => EitherT.leftT( NonEmptyList.of( TransactionError( "An error occurred" ) ) ) }
  }

  def mockerFindWithTaskSuccessfully( user: User, date: LocalDateTime )( implicit dependency: Dependency ) = {
    dependency.userRepo.add _ expects ( user, date ) returning Reader { _ => EitherT.rightT( Done ) }
  }

}
