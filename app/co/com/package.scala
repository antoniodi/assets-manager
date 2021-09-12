package co

import java.util.concurrent.{ SynchronousQueue, ThreadPoolExecutor, TimeUnit }

package object com {

  val maxCommandThreadPool = 10
  val maxQueryThreadPool = 10

  def newCustomCachedThreadPool( maximumPoolSize: Int = Integer.MAX_VALUE, keepAlive: Long = 60L, unit: TimeUnit = TimeUnit.SECONDS ): ThreadPoolExecutor = {
    new ThreadPoolExecutor( 0, maximumPoolSize, keepAlive, unit, new SynchronousQueue[Runnable] )
  }

}
