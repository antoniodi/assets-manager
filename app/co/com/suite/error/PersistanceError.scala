package co.com.suite.error

sealed trait PersistanceError extends ApplicationError

final case class TransactionError( error: String ) extends PersistanceError {
  override def errorMessage: String = error
}

final case class SaveError( tableName: String ) extends PersistanceError {
  override def errorMessage: String = s"An error occurred trying to save a $tableName."
}

final case class FindError( tableName: String, id: String ) extends PersistanceError {
  override def errorMessage: String = s"An error occurred trying to find a $tableName with id: $id."
}
