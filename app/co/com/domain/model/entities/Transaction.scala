package co.com.domain.model.entities

import java.time.LocalDateTime

trait Transaction {
  def id: String
  def date: LocalDateTime
  def currencyAmount: CurrencyAmount
  def description: String
}

case class Income( id: String, date: LocalDateTime, currencyAmount: CurrencyAmount, description: String ) extends Transaction

case class Expense( id: String, date: LocalDateTime, currencyAmount: CurrencyAmount, description: String ) extends Transaction