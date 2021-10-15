package co.com.domain.model.entities

sealed trait Asset {
  def id: String

  def description: String

  def transactions: List[Transaction]
}

sealed trait Salable {
  def cost: CurrencyAmount
}

sealed trait Affordable {
  def sellPrice: Option[CurrencyAmount]
}

sealed trait Merchantable extends Salable with Affordable

case class RealEstate(
    id: String,
    description: String,
    cost: CurrencyAmount,
    transactions: List[Transaction] = Nil,
    sellPrice: Option[CurrencyAmount] = None
) extends Asset with Merchantable

//case class LoanToThirdParty(id:String, description: String, transactions: List[Transaction]) extends Asset

