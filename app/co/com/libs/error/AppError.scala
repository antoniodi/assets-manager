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

case class InfrastructureError( message: String ) extends AppError {
  val errorType: ErrorType = Infrastructure
}