package co.com.libs.error

sealed trait AppError {

  def errorType: ErrorType
  def message: String
}

case class BusinessError( message: String ) extends AppError {
  val errorType: ErrorType = Business
}

case class ApplicationError( message: String ) extends AppError {
  val errorType: ErrorType = Infrastructure
}

trait InfrastructureError extends AppError {
  val errorType: ErrorType = Infrastructure
  def message: String
}

case class TransformationError( message: String ) extends InfrastructureError

case class DataBaseError( message: String ) extends InfrastructureError