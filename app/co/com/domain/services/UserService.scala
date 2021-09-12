package co.com.domain.services

import akka.Done
import cats.data.NonEmptyList
import co.com.domain.model.entities.User
import co.com.suite.error.{ ApplicationError, UserAlreadyExits, UserNotFound }

trait UserService {

  def validateUser( user: Option[User] ): Either[NonEmptyList[ApplicationError], User] = {
    user.fold[Either[NonEmptyList[ApplicationError], User]]( Left( NonEmptyList.of( UserNotFound ) ) )( Right( _ ) )
  }

  def validateExistingUser( user: Option[User] ): Either[NonEmptyList[ApplicationError], Done] = {
    user.fold[Either[NonEmptyList[ApplicationError], Done]] {
      Right( Done )
    } { user =>
      Left( NonEmptyList.of( UserAlreadyExits( user.username ) ) )
    }
  }
}

object UserService extends UserService
