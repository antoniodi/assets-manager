package co.com.infrastructure.persistence.transformers

import co.com.domain.model.entities.User
import co.com.infrastructure.persistence.tables.UserRow

import java.sql.Timestamp
import java.time.LocalDateTime

object UserTransformer {

  def userToUserRow( user: User, validFrom: LocalDateTime ): UserRow = {
    UserRow( user.id, user.username, user.email, Timestamp.valueOf( validFrom ) )
  }

  def userRowToUser( user: UserRow ): User = {
    User( user.id, user.username, user.email )
  }

}
