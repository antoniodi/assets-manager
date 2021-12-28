package co

import squants.market.{ Currency, EUR, MoneyContext, USD }

import java.util.concurrent.{ SynchronousQueue, ThreadPoolExecutor, TimeUnit }

package object com {

  val maxCommandThreadPool = 10
  val maxQueryThreadPool = 10

  def newCustomCachedThreadPool( maximumPoolSize: Int = Integer.MAX_VALUE, keepAlive: Long = 60L, unit: TimeUnit = TimeUnit.SECONDS ): ThreadPoolExecutor = {
    new ThreadPoolExecutor( 2, maximumPoolSize, keepAlive, unit, new SynchronousQueue[Runnable] )
  }

  object COP extends Currency( "COP", "Colombian Peso", "$", 2 )

  val moneyContext: MoneyContext = MoneyContext( COP, Set( COP, USD, EUR ), Nil, allowIndirectConversions = false )

}
