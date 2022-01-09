package co.com.infrastructure.persistence

import slick.lifted.TableQuery

package object tables {

  val users = TableQuery[UserTable]
  val expenses = TableQuery[ExpenseTable]
  val liabilities = TableQuery[LiabilityTable]
  val asset = TableQuery[AssetTable]

}
