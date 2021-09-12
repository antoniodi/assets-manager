package co.com.infrastructure

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

package object persistence {

  lazy val dbConfigPostgres = DatabaseConfig.forConfig[JdbcProfile]( "slick.dbs.postgres" )
  lazy val dbConfigReadOnlyPostgres = DatabaseConfig.forConfig[JdbcProfile]( "slick.dbs.postgres-read-only" )

  lazy val dbConfigH2 = DatabaseConfig.forConfig[JdbcProfile]( "slick.dbs.default" )

  def generarUUID: String = {
    java.util.UUID.randomUUID().toString
  }

}
