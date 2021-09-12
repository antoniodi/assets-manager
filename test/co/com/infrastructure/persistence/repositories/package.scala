package co.com.infrastructure.persistence

import co.com.infrastructure.persistence.tables.{ UserRow, users }
import com.typesafe.config.{ Config, ConfigFactory }
import slick.basic.DatabaseConfig
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import java.sql.Timestamp

package object repositories {

  lazy val configFileTest: Config = ConfigFactory.load( "test.conf" )

  lazy val dbConfigH2Test = DatabaseConfig.forConfig[JdbcProfile]( "slick.dbs.h2", configFileTest )

  val schema_users_management = users.schema

  val setup_test_users_management = DBIO.seq(
    schema_users_management.create,

    users += UserRow( "a-1", "luiscocr", "email@gmail.com", Timestamp.valueOf( "2020-11-25 10:10:10" ) )
  )

}
