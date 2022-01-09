package co.com.domain.model.entities

import squants.market.Money

trait Liability {
  def id: String
  def description: String
  def totalDebt: Money
  //  def state: String
}

case class Mortgage( id: String, description: String, totalDebt: Money ) extends Liability
