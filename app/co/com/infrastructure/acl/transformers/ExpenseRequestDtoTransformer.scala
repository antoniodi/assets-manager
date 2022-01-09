package co.com.infrastructure.acl.transformers

import co.com.domain.model.entities.{ Asset, Expense, Liability }
import co.com.domain.model.types.ExpenseCategory
import co.com.infrastructure.acl.dtos.ExpenseRequestDto
import co.com.infrastructure.config.Dependency
import co.com.libs.error.AppError
import zio.ZIO

trait ExpenseRequestDtoTransformer {

  def toExpense( transactionId: String, dto: ExpenseRequestDto, asset: Option[Asset], liability: Option[Liability] ): ZIO[Dependency, AppError, Expense] = {
    for {
      dependency <- ZIO.environment[Dependency]
      category <- ExpenseCategory( dto.category )
      money <- dependency.currencyAmountDtoTransformer.toMoney( dto.currencyAmount.currency, dto.currencyAmount.amount )
      now = dependency.dateHelper.getNow
    } yield Expense( transactionId, now, category, dto.description, money, asset, liability )
  }

}

object ExpenseRequestDtoTransformer extends ExpenseRequestDtoTransformer