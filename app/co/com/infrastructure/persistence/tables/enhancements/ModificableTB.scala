package co.com.infrastructure.persistence.tables.enhancements

import slick.lifted.Rep

import java.sql.Timestamp

trait ModificableTB {

  def validFrom: Rep[Timestamp]
  def validTo: Rep[Option[Timestamp]]

}
