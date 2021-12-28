package co.com.domain.model.types

import co.com.libs.error.{ BusinessErrorBase, InvalidCategoryError }
import zio.IO

sealed trait Category {
  def code: String
  def description: String
}

object Rent extends Category with ExpenseCategory {
  def code: String = "RENT"
  def description: String = "Rent"
}

object Other extends Category with ExpenseCategory {
  def code: String = "OTHER"
  def description: String = "Other"
}

trait IncomeCategory extends Category

trait ExpenseCategory extends Category

object ExpenseCategory {
  def apply( categoryCode: String ): IO[BusinessErrorBase, ExpenseCategory] = {
    categoryCode match {
      case "BUSINESS_EXPENSE" => IO.succeed( BusinessExpense )
      case "FOOD"             => IO.succeed( Food )
      case "ENTERTAINMENT"    => IO.succeed( Entertainment )
      case "SELF_IMPROVEMENT" => IO.succeed( SelfImprovement )
      case "TRAVEL"           => IO.succeed( Travel )
      case "TRANSPORTATION"   => IO.succeed( Transportation )
      case "MEDICAL"          => IO.succeed( Medical )
      case "RENT"             => IO.succeed( Rent )
      case "OTHER"            => IO.succeed( Other )
      case _                  => IO.fail( InvalidCategoryError( s"The expense category: [$categoryCode], is invalid." ) )
    }
  }
}

object BusinessExpense extends ExpenseCategory {
  def code: String = "BUSINESS_EXPENSE"
  def description: String = "BusinessExpense"
}

object Food extends ExpenseCategory {
  def code: String = "FOOD"
  def description: String = "Food"
}

object Entertainment extends ExpenseCategory {
  def code: String = "ENTERTAINMENT"
  def description: String = "EntertainmentFood"
}

object SelfImprovement extends ExpenseCategory {
  def code: String = "SELF_IMPROVEMENT"
  def description: String = "SelfImprovement"
}

object Travel extends ExpenseCategory {
  def code: String = "TRAVEL"
  def description: String = "Travel"
}

object Transportation extends ExpenseCategory {
  def code: String = "TRANSPORTATION"
  def description: String = "Transportation"
}

object Medical extends ExpenseCategory {
  def code: String = "MEDICAL"
  def description: String = "Medical"
}

