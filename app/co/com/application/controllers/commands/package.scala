package co.com.application.controllers

import co.com.{ maxCommandThreadPool, newCustomCachedThreadPool }
import monix.execution.ExecutionModel.AlwaysAsyncExecution
import monix.execution.schedulers.ExecutorScheduler
import monix.execution.{ Features, UncaughtExceptionReporter }

import scala.concurrent.{ ExecutionContext, ExecutionContextExecutorService }

package object commands {

  lazy val commandExecutionContext: ExecutionContextExecutorService = ExecutionContext.fromExecutorService( newCustomCachedThreadPool( maxCommandThreadPool ) )

  lazy val commandScheduler: ExecutorScheduler = ExecutorScheduler(
    newCustomCachedThreadPool( maxCommandThreadPool ),
    UncaughtExceptionReporter( t => println( s"this should not happen: ${t.getMessage}" ) ),
    AlwaysAsyncExecution,
    Features.empty
  )

}
