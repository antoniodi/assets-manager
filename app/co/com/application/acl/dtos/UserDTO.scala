package co.com.application.acl.dtos

import co.com.domain.model.entities.User

case class UserDTO( username: String, email: String )

object UserDTO {

  def toUser( userId: String, user: UserDTO ) = User( userId, user.username, user.email )
}