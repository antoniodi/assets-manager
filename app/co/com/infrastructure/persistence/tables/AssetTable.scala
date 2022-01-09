package co.com.infrastructure.persistence.tables

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

final case class AssetRow(
    id: String,
    description: String
)

class AssetTable( tag: Tag ) extends Table[AssetRow]( tag, "ASSET" ) {

  def id = column[String]( "ID" )
  def description = column[String]( "DESCRIPTION" )

  def pk = primaryKey( "ASSET_PK", id )

  def * = ( id, description ).mapTo[AssetRow]
}
