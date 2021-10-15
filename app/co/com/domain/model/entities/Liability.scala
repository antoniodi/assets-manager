package co.com.domain.model.entities

trait Liability {
  def id: String
  def description: String
  def totalDebt: CurrencyAmount
  def pendingDebt: CurrencyAmount
}

//case class Mortgage(id: String, ) extends Liability
