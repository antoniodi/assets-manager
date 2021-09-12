package co.com.suite.error

sealed trait DomainValidation extends ApplicationError

case object UserNotFound extends DomainValidation {
  def errorMessage: String = "User not found."
}

case class UserAlreadyExits( username: String ) extends DomainValidation {
  def errorMessage: String = s"User with the username: $username already exist."
}
