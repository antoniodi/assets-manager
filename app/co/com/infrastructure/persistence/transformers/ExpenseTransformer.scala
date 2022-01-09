package co.com.infrastructure.persistence.transformers

import co.com.domain.model.entities.Expense
import co.com.domain.model.types.ExpenseCategory
import co.com.infrastructure.acl.helpers.DateHelper
import co.com.infrastructure.acl.transformers.common.CurrencyAmountTransformer
import co.com.infrastructure.persistence.tables.ExpenseRow
import co.com.libs.error.AppError
import zio.IO

object ExpenseTransformer {

  def toExpense( expense: ExpenseRow ): IO[AppError, Expense] = {
    for {
      category <- ExpenseCategory( expense.category )
      money <- CurrencyAmountTransformer.toMoney( expense.currency, expense.amount )
      now = DateHelper.getNow
    } yield Expense( expense.id, now, category, expense.description, money, None, None )
  }

}
