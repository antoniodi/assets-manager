package co.com.domain.model.types

sealed trait IncomeType

object IncomeType

case object Salary extends IncomeType
case object Rent extends IncomeType

