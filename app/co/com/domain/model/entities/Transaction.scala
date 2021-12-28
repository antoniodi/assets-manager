package co.com.domain.model.entities

import co.com.domain.model.types.{ Category, ExpenseCategory, IncomeCategory }
import squants.market.Money

import java.time.LocalDateTime

trait Transaction {
  def id: TransactionId

  def date: LocalDateTime

  def category: Category

  def currencyAmount: Money

  def description: String

  def asset: Option[Asset]
}

case class TransactionId( id: String )

case class Income( id: TransactionId, date: LocalDateTime, category: IncomeCategory, currencyAmount: Money, description: String, asset: Option[Asset] = None ) extends Transaction

case class Expense( id: TransactionId, date: LocalDateTime, category: ExpenseCategory, currencyAmount: Money, description: String, asset: Option[Asset] = None, liability: Option[Liability] = None ) extends Transaction