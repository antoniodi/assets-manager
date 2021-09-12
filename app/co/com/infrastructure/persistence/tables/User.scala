package co.com.infrastructure.persistence.tables

import co.com.infrastructure.persistence.tables.enhancements.ModificableTB
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.sql.Timestamp

final case class UserRow(
    id: String,
    username: String,
    email: String,
    validFrom: Timestamp,
    validTo: Option[Timestamp] = None
)

class UserTable( tag: Tag ) extends Table[UserRow]( tag, "USER" ) with ModificableTB {

  def id = column[String]( "ID" )
  def username = column[String]( "USERNAME" )
  def email = column[String]( "EMAIL" )
  def validFrom = column[Timestamp]( "START_DATE" )
  def validTo = column[Option[Timestamp]]( "END_DATE", O.Default( None ) )

  def pk = primaryKey( "USER_PK", id )

  def idx_01 = index( "user_username_unique", username, unique = true )

  def idx_02 = index( "user_email_unique", email, unique = true )

  def * = ( id, username, email, validFrom, validTo ).mapTo[UserRow]
}