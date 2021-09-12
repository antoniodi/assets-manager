package co.com.application.controllers

import co.com.{ maxQueryThreadPool, newCustomCachedThreadPool }
import monix.execution.ExecutionModel.AlwaysAsyncExecution
import monix.execution.schedulers.ExecutorScheduler
import monix.execution.{ Features, UncaughtExceptionReporter }

import scala.concurrent.{ ExecutionContext, ExecutionContextExecutorService }

package object queries {

  lazy val queryExecutionContext: ExecutionContextExecutorService = ExecutionContext.fromExecutorService( newCustomCachedThreadPool( maxQueryThreadPool ) )

  lazy val queryScheduler: ExecutorScheduler = ExecutorScheduler(
    newCustomCachedThreadPool( maxQueryThreadPool ),
    UncaughtExceptionReporter( t => println( s"this should not happen: ${t.getMessage}" ) ),
    AlwaysAsyncExecution,
    Features.empty
  )

}
