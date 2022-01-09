package co.com.domain.model.entities

import squants.market.Money

sealed trait Asset {
  def id: String

  def description: String

  def transactions: List[Transaction]
  //  def state: String
}

sealed trait Salable {
  def cost: Money
}

sealed trait Affordable {
  def sellPrice: Option[Money]
}

sealed trait Merchantable extends Salable with Affordable

case class RealEstate(
    id: String,
    description: String,
    cost: Money,
    address: String,
    transactions: List[Transaction] = Nil,
    sellPrice: Option[Money] = None
) extends Asset with Merchantable

case class LoanToThirdParty( id: String, description: String, transactions: List[Transaction] = Nil ) extends Asset

case class Stocks( id: String, description: String, transactions: List[Transaction] = Nil ) extends Asset