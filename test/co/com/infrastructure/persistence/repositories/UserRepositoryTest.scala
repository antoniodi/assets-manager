package co.com.infrastructure.persistence.repositories

import akka.Done
import cats.data.NonEmptyList
import co.com.factories.UserFactory.LouisUser
import co.com.suite.error.ApplicationError
import co.com.tool.FutureTool.waitForFutureResult
import co.com.{ TestKit, TestKitBase }
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import slick.dbio.DBIO
import slick.jdbc.H2Profile.api._

import java.time.LocalDateTime

class UserRepositoryTest extends TestKit with TestKitBase with BeforeAndAfter {

  before {
    val setup = dbConfigH2Test.db.run( setup_test_users_management )
    waitForFutureResult( setup )
  }

  after {
    waitForFutureResult( dbConfigH2Test.db.run( DBIO.seq( schema_users_management.drop ) ) )
  }

  "UserRepositoryTest" should {

    "to add new user" when {
      val validFrom = LocalDateTime.of( 2020, 11, 25, 10, 10, 10 )

      "the user doesn't exists" should {
        "return Done" in {
          val user = LouisUser.copy( id = "a-a", username = "newuser", email = "newuser@gmail.com" )

          val result = waitForFutureResult(
            UserRepository.add( user, validFrom ).run( dbConfigH2Test ).value.runToFuture
          )

          result mustBe Right( Done )
        }
      }

      "exists an user with the same id" should {
        "return an error" in {
          val user = LouisUser.copy( username = "newuser", email = "newuser@gmail.com" )

          val result = waitForFutureResult(
            UserRepository.add( user, validFrom ).run( dbConfigH2Test ).value.runToFuture
          )

          result.isInstanceOf[Left[NonEmptyList[ApplicationError], Done]] mustBe true
        }
      }

      "exists an user with the same username" should {
        "return an error" in {
          val user = LouisUser.copy( id = "a-a", email = "newuser@gmail.com" )

          val result = waitForFutureResult( UserRepository.add( user, validFrom ).run( dbConfigH2Test ).value.runToFuture )

          result.isInstanceOf[Left[NonEmptyList[ApplicationError], Done]] mustBe true
        }
      }

      "exists an user with the same email" should {
        "return an error" in {
          val user = LouisUser.copy( id = "a-a", username = "newuser" )

          val result = waitForFutureResult( UserRepository.add( user, validFrom ).run( dbConfigH2Test ).value.runToFuture )

          result.isInstanceOf[Left[NonEmptyList[ApplicationError], Done]] mustBe true
        }
      }
    }

    "to find user by username with Task" when {
      "the user exists" should {
        "return the user" in {
          val username = "luiscocr"

          val result = waitForFutureResult( UserRepository.findWithTask( username ).run( dbConfigH2Test ).value.runToFuture )

          result mustBe Right( Some( LouisUser ) )
        }
      }

      "the user doesn't exists" should {
        "return any user" in {
          val username = "anyUsername"

          val result = waitForFutureResult( UserRepository.findWithTask( username ).run( dbConfigH2Test ).value.runToFuture )

          result mustBe Right( None )
        }
      }
    }

    "to find user by username with Future" when {
      "the user exists" should {
        "return the user" in {
          val username = "luiscocr"

          val result = waitForFutureResult( UserRepository.findWithFuture( username ).run( dbConfigH2Test ).value )

          result mustBe Right( Some( LouisUser ) )
        }
      }

      "the user doesn't exists" should {
        "return any user" in {
          val username = "anyUsername"

          val result = waitForFutureResult( UserRepository.findWithFuture( username ).run( dbConfigH2Test ).value )

          result mustBe Right( None )
        }
      }
    }
  }
}
