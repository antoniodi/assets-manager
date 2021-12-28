package co.com.domain.model.entities

import squants.market.Money

sealed trait Asset {
  def id: AssetId

  def description: String

  def transactions: List[Transaction]
  //  def state: String
}

case class AssetId( id: String )

sealed trait Salable {
  def cost: Money
}

sealed trait Affordable {
  def sellPrice: Option[Money]
}

sealed trait Merchantable extends Salable with Affordable

case class RealEstate(
    id: AssetId,
    description: String,
    cost: Money,
    address: String,
    transactions: List[Transaction] = Nil,
    sellPrice: Option[Money] = None
) extends Asset with Merchantable

case class LoanToThirdParty( id: AssetId, description: String, transactions: List[Transaction] ) extends Asset

case class Stocks( id: AssetId, description: String, transactions: List[Transaction] ) extends Asset