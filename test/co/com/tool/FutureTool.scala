package co.com.tool

import scala.concurrent.{ Await, Future }
import scala.concurrent.duration.Duration
import scala.util.Try

object FutureTool {

  def waitForFutureReady[T]( future: Future[T] ): Try[T] =
    Await.ready( future, Duration.Inf ).value.get

  def waitForFutureResult[T]( future: Future[T] ): T =
    Await.result( future, Duration.Inf )

}
