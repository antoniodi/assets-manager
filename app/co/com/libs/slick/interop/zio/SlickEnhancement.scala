package co.com.libs.slick.interop.zio

import slick.basic.DatabaseConfig
import slick.dbio.DBIO
import slick.jdbc.JdbcProfile
import zio.ZIO

import scala.concurrent.ExecutionContext

object SlickEnhancement {

  implicit class ZIOSlickOps( private val zio: ZIO.type ) extends AnyVal {

    def fromDBIO[R]( f: ExecutionContext => DBIO[R] ): ZIO[DatabaseConfig[JdbcProfile], Throwable, R] =
      for {
        db <- ZIO.accessZIO[DatabaseConfig[JdbcProfile]]( config => ZIO( config.db ) )
        result <- ZIO.fromFuture( ec => db.run( f( ec ) ) )
      } yield result

    def fromDBIO[R]( dbio: => DBIO[R] ): ZIO[DatabaseConfig[JdbcProfile], Throwable, R] =
      for {
        db <- ZIO.accessZIO[DatabaseConfig[JdbcProfile]]( config => ZIO( config.db ) )
        result <- ZIO.fromFuture( _ => db.run( dbio ) )
      } yield result
  }
}
