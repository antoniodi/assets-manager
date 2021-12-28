package co.com.infrastructure.acl.transformers

import co.com.domain.model.entities.{ Asset, Expense, Liability, TransactionId }
import co.com.domain.model.types.ExpenseCategory
import co.com.infrastructure.acl.dtos.ExpenseRequestDto
import co.com.infrastructure.config.Dependency
import co.com.libs.error.AppError
import zio.ZIO

trait ExpenseRequestDtoTransformer {

  def toExpense( transactionId: TransactionId, dto: ExpenseRequestDto, asset: Option[Asset], liability: Option[Liability] ): ZIO[Dependency, AppError, Expense] = {
    for {
      dependency <- ZIO.environment[Dependency]
      category <- ExpenseCategory( dto.category )
      money <- dependency.currencyAmountDtoTransformer.toMoney( dto.currencyAmount )
      now = dependency.dateHelper.getNow
    } yield Expense( transactionId, now, category, money, dto.description, asset, liability )
  }

}

object ExpenseRequestDtoTransformer extends ExpenseRequestDtoTransformer