package co.com.application.services

import co.com.tool.FutureTool.waitForFutureResult
import co.com.{ TestKit, TestKitBase }
import monix.eval.Task
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper

import scala.concurrent.Future
import scala.util.Random

class AsynchronousServiceTest extends TestKit with TestKitBase {

  "AsynchronousService" should {

    "to execute Async" when {
      "zip two futures" should {
        "execute future eager" in {

          val future = Future.successful( Random.nextInt() )

          val zipFuture: Future[( Int, Int )] = future.zip( future )

          val result = waitForFutureResult( zipFuture )

          result._1 mustBe result._2
        }
      }

      "zip two futures transparent" should {
        "execute future eager" in {

          val zipFuture: Future[( Int, Int )] = Future.successful( Random.nextInt() ).zip( Future.successful( Random.nextInt() ) )

          val result = waitForFutureResult( zipFuture )

          assert( result._1 != result._2 )
        }
      }

      "zip two task" should {
        "execute task lazy" in {

          val task = Task( Random.nextInt() )

          val zipTask = Task.parZip2( task, task )

          val result = waitForFutureResult( zipTask.runToFuture )

          assert( result._1 != result._2 )
        }
      }

      "zip two task transparent" should {
        "execute task lazy" in {

          val zipTask: Task[( Int, Int )] = Task.parZip2( Task.now( Random.nextInt() ), Task.now( Random.nextInt() ) )

          val result = waitForFutureResult( zipTask.runToFuture )

          assert( result._1 != result._2 )
        }
      }

      "zip two task output" should {
        "execute task lazy" in {

          val task = Task.now( Random.nextInt() )

          val zipTask = Task.parZip2( task, task )

          val result = waitForFutureResult( zipTask.runToFuture )

          result._1 mustBe result._2
        }
      }
    }
  }

}
