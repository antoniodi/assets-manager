package co.com.mock.repositories

import co.com.TestKitBase

trait MockPersistenceUserService extends TestKitBase {

  //  def mockerFindWithTaskSuccessfully( username: String, result: Option[User] )( implicit dependency: Dependency ) = {
  //    dependency.userRepo.findWithTask _ expects username returning Reader { _ => EitherT.rightT( result ) }
  //  }
  //
  //  def mockerFindWithTaskWithError( implicit dependency: Dependency ) = {
  //    dependency.userRepo.findWithTask _ expects * returning Reader { _ => EitherT.leftT( NonEmptyList.of( TransactionError( "An error occurred" ) ) ) }
  //  }
  //
  //  def mockerFindWithTaskSuccessfully( user: User, date: LocalDateTime )( implicit dependency: Dependency ) = {
  //    dependency.userRepo.add _ expects ( user, date ) returning Reader { _ => EitherT.rightT( Done ) }
  //  }

}
