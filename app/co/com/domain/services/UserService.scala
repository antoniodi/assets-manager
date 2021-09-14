package co.com.domain.services

import akka.Done
import co.com.domain.model.entities.User
import co.com.libs.error.{ AppError, BusinessError }
import zio.IO

trait UserService {

  def validateUser( user: Option[User] ): IO[AppError, User] = {
    user.fold[IO[AppError, User]] {
      IO.fail( BusinessError( s"User not found" ) )
    } { user =>
      IO.succeed( user )
    }
  }

  def validateExistingUser( user: Option[User] ): IO[AppError, Done] = {
    user.fold[IO[AppError, Done]] {
      IO.succeed( Done )
    } { user =>
      IO.fail( BusinessError( s"The user ${user.username} already exits" ) )
    }
  }
}

object UserService extends UserService
