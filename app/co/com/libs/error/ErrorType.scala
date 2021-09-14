package co.com.libs.error

sealed trait ErrorType

case object Application extends ErrorType

case object Business extends ErrorType

case object Infrastructure extends ErrorType

