package co.com.application.services

import akka.Done
import cats.data.NonEmptyList
import co.com.factories.UserDTOFactory.LouisUserDTO
import co.com.factories.UserFactory.LouisUser
import co.com.mock.repositories.MockPersistenceUserService
import co.com.suite.error.{ TransactionError, UserAlreadyExits }
import co.com.tool.FutureTool.waitForFutureResult
import co.com.{ FalseConfigurations, TestKit }
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper

import java.time.LocalDateTime

class PersistenceUserServiceTest extends TestKit with MockPersistenceUserService {

  "PersistenceUserServiceTest" should {

    "to save user" when {
      val userId = "u-1"
      val currentDate = LocalDateTime.of( 2020, 11, 25, 10, 10, 10 )

      "the user doesn't exists" should {
        "return Done" in {
          implicit val dependency: FalseConfigurations = getInstanceFalseConfigurations
          val user = LouisUser.copy( id = userId )
          mockerFindWithTaskSuccessfully( LouisUser.username, None ).once()
          ( dependency.userService.validateExistingUser _ expects None returning Right( Done ) ).once()
          ( () => dependency.userRepo.generateId() ).expects().returning( userId ).once()
          ( () => dependency.serviceHelper.getCurrentLocalDateTime() ).expects().returning( currentDate ).once()
          mockerFindWithTaskSuccessfully( user, currentDate ).once()

          val result = waitForFutureResult( PersistenceUserService.saveUser( LouisUserDTO ).run( dependency ).value.runToFuture )

          result mustBe Right( Done )
        }
      }

      "the user already exists" should {
        "indicate that the user already exists" in {
          implicit val dependency: FalseConfigurations = getInstanceFalseConfigurations
          val username = LouisUser.username
          val expectedError = Left( NonEmptyList.of( UserAlreadyExits( username ) ) )
          mockerFindWithTaskSuccessfully( username, Some( LouisUser ) ).once()
          ( dependency.userService.validateExistingUser _ expects Some( LouisUser ) returning expectedError ).once()

          val result = waitForFutureResult( PersistenceUserService.saveUser( LouisUserDTO ).run( dependency ).value.runToFuture )

          result mustBe expectedError
        }
      }

      "occurs an error trying to find the user" should {
        "indicate that the user already exists" in {
          implicit val dependency: FalseConfigurations = getInstanceFalseConfigurations
          val expectedError = Left( NonEmptyList.of( TransactionError( "An error occurred" ) ) )
          mockerFindWithTaskWithError.once()

          val result = waitForFutureResult( PersistenceUserService.saveUser( LouisUserDTO ).run( dependency ).value.runToFuture )

          result mustBe expectedError
        }
      }
    }
  }

}
