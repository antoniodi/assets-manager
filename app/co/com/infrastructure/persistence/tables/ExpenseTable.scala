package co.com.infrastructure.persistence.tables

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.sql.Timestamp

case class ExpenseRow(
    id: String,
    date: Timestamp,
    category: String,
    description: String,
    currency: String,
    amount: Double,
    idAsset: Option[String],
    idLiability: Option[String]
)

class ExpenseTable( tag: Tag ) extends Table[ExpenseRow]( tag, "EXPENSE" ) {

  def id = column[String]( "ID" )
  def date = column[Timestamp]( "DATE" )
  def category = column[String]( "CATEGORY" )
  def description = column[String]( "DESCRIPTION" )
  def currency = column[String]( "CURRENCY" )
  def amount = column[Double]( "AMOUNT", O.SqlType( "NUMERIC(15,2)" ) )
  def idAsset = column[Option[String]]( "ID_ASSET" )
  def idLiability = column[Option[String]]( "ID_LIABILITY" )

  def pk = primaryKey( "USER_PK", id )

  def idx_01 = index( "expense_date", date )

  //  def idAsset_fk = foreignKey( "ASSET_FK01", cdObjetoAsegurable, TB_ObjetoAsegurable )( _.cdObjetoAsegurable, onDelete = ForeignKeyAction.Cascade )

  //  def idLiability_fk = foreignKey( "LIABILITY_FK02", cdObjetoAsegurable, TB_ObjetoAsegurable )( _.cdObjetoAsegurable, onDelete = ForeignKeyAction.Cascade )

  def * = ( id, date, category, description, currency, amount, idAsset, idLiability ).mapTo[ExpenseRow]
}