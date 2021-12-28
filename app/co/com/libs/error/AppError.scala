package co.com.libs.error

sealed trait AppError {

  def errorType: ErrorType
  def message: String
  def error: Option[Throwable]
}

trait BusinessErrorBase extends AppError {
  val errorType: ErrorType = Business
}

case class BusinessError( message: String, error: Option[Throwable] = None ) extends BusinessErrorBase

case class InvalidCategoryError( message: String, error: Option[Throwable] = None ) extends BusinessErrorBase

trait ApplicationErrorBase extends AppError {
  val errorType: ErrorType = Application
}

case class ApplicationError( message: String, error: Option[Throwable] = None ) extends ApplicationErrorBase

trait InfrastructureErrorBase extends AppError {
  val errorType: ErrorType = Infrastructure
}

case class InfrastructureError( message: String, error: Option[Throwable] = None ) extends InfrastructureErrorBase

case class TransformationError( message: String, error: Option[Throwable] = None ) extends InfrastructureErrorBase

case class DataBaseError( message: String, error: Option[Throwable] = None ) extends InfrastructureErrorBase