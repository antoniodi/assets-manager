package co.com.infrastructure.persistence.tables

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

case class LiabilityRow(
    id: String,
    `type`: String,
    description: String,
    totalDebtCurrency: String,
    totalDebtAmount: Double
)

class LiabilityTable( tag: Tag ) extends Table[LiabilityRow]( tag, "LIABILITY" ) {

  def id = column[String]( "ID" )
  def `type` = column[String]( "TYPE" )
  def description = column[String]( "DESCRIPTION" )
  def totalDebtCurrency = column[String]( "TOTAL_DEBT_CURRENCY" )
  def totalDebtAmount = column[Double]( "TOTAL_DEBT_AMOUNT", O.SqlType( "NUMERIC(15,2)" ) )

  def pk = primaryKey( "LIABILITY_PK", id )

  def * = ( id, `type`, description, totalDebtCurrency, totalDebtAmount ).mapTo[LiabilityRow]
}