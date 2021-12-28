package co.com.infrastructure

import cats.data.{ EitherT, NonEmptyList }
import co.com.libs.error
import monix.eval.Task

import scala.concurrent.Future

object Types {

  type EitherTResult[A] = EitherT[Task, NonEmptyList[error.ApplicationError], A]

  type EitherFResult[A] = EitherT[Future, NonEmptyList[error.ApplicationError], A]

}
