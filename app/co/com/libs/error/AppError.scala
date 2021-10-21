package co.com.libs.error

sealed trait AppError {

  def errorType: ErrorType
  def message: String
}

trait BusinessErrorBase extends AppError {
  val errorType: ErrorType = Business
  def message: String
}

case class BusinessError( message: String ) extends BusinessErrorBase

trait ApplicationErrorBase extends AppError {
  val errorType: ErrorType = Application
  def message: String
}

case class ApplicationError( message: String ) extends ApplicationErrorBase

trait InfrastructureErrorBase extends AppError {
  val errorType: ErrorType = Infrastructure
  def message: String
}

case class InfrastructureError( message: String ) extends InfrastructureErrorBase

case class TransformationError( message: String ) extends InfrastructureErrorBase

case class DataBaseError( message: String ) extends InfrastructureErrorBase