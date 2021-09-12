package co.com.suite.error

trait ServiceError extends ApplicationError

final case class ConsumeServiceError( serviceName: String ) extends ServiceError {
  override def errorMessage: String = s"An error occurred trying to consume the service $serviceName."
}

final case class DetailServiceError( error: String ) extends ServiceError {
  override def errorMessage: String = s"$error."
}