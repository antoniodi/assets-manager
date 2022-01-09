package co.com.domain.model.entities

import co.com.domain.model.types.{ Category, ExpenseCategory, IncomeCategory }
import squants.market.Money

import java.time.LocalDateTime

trait Transaction {

  def id: String

  def date: LocalDateTime

  def category: Category

  def description: String

  def value: Money

  def asset: Option[Asset]
}

case class Income( id: String, date: LocalDateTime, category: IncomeCategory, description: String, value: Money, asset: Option[Asset] = None ) extends Transaction

case class Expense( id: String, date: LocalDateTime, category: ExpenseCategory, description: String, value: Money, asset: Option[Asset] = None, liability: Option[Liability] = None ) extends Transaction