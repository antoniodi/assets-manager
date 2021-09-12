package co.com.application.services

import cats.data.EitherT
import co.com.{ TestKit, TestKitBase }
import co.com.tool.FutureTool.waitForFutureResult
import monix.eval.Task
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper

class ParallelProcessTest extends TestKit with TestKitBase {

  "ParallelProcess" should {
    "Task in parallel" when {

      def taskInParallel() = {
        val firstTen = ( n: Int ) => ( 1 to n ).foreach( println )

        val eitherT1 = EitherT.right[Throwable]( Task( Right( firstTen( 100 ) ) ) )
        val eitherT2 = EitherT.right[Throwable]( Task( Task( Right( firstTen( 100 ) ) ) ) )

        EitherT( Task.parMap2( eitherT1.value, eitherT2.value ) { ( taskA, taskB ) =>
          {
            for {
              a <- taskA
              b <- taskB
            } yield println( s"sdf $a $b" )
          }
        } )
      }

      "task in parallel" should {
        "process in parallel" in {
          val result = waitForFutureResult( taskInParallel().value.runToFuture )

          result mustBe Right( () )
        }
      }
    }

    "Task in parallel with raise" when {

      def taskInParallel() = {
        val firstTen = ( n: Int ) => ( 1 to n ).foreach( println )
        val task1 = Task( firstTen( 10 ) )
        val task2 = Task( firstTen( 10 ) )
        //val task2: Task[Unit] = Task.raiseError( new RuntimeException( "20" ) )

        Task.parMap2( task1, task2 ) { ( taskA, taskB ) =>
          {
            println( s"sdf $taskA $taskB" )
          }
        }
      }

      "task in parallel" should {
        "process in parallel" in {

          taskInParallel().runAsync {
            case Left( value )  => fail()
            case Right( value ) => fail()
          }
        }
      }
    }
  }

}
