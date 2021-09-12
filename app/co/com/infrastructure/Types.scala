package co.com.infrastructure

import cats.data.{ EitherT, NonEmptyList }
import co.com.suite.error.ApplicationError
import monix.eval.Task
import zio.{ RIO, ZIO }

import scala.concurrent.Future

object Types {

  type EitherTResult[A] = EitherT[Task, NonEmptyList[ApplicationError], A]

  type EitherFResult[A] = EitherT[Future, NonEmptyList[ApplicationError], A]

  type ZIOS[-R, +A] = ZIO[R, NonEmptyList[ApplicationError], A]

}
