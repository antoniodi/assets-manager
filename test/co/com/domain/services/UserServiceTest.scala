package co.com.domain.services

import akka.Done
import cats.data.NonEmptyList
import co.com.factories.UserFactory.LouisUser
import co.com.suite.error.{ UserAlreadyExits, UserNotFound }
import co.com.{ TestKit, TestKitBase }
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper

class UserServiceTest extends TestKit with TestKitBase {

  "UserServiceTest" should {

    "to validate user" when {
      "the user is found" should {
        "return Done" in {
          val result = UserService.validateUser( Some( LouisUser ) )

          result mustBe Right( LouisUser )
        }
      }

      "the user not found" should {
        "indicate that the user not found" in {
          val result = UserService.validateUser( None )

          result mustBe Left( NonEmptyList.of( UserNotFound ) )
        }
      }
    }

    "to validate existing user" when {
      "the user not found" should {
        "return done" in {
          val result = UserService.validateExistingUser( None )

          result mustBe Right( Done )
        }
      }

      "the user is found" should {
        "indicate that the user already exists" in {
          val result = UserService.validateExistingUser( Some( LouisUser ) )

          result mustBe Left( NonEmptyList.of( UserAlreadyExits( "luiscocr" ) ) )
        }
      }
    }
  }
}
