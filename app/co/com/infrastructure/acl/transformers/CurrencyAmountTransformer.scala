package co.com.infrastructure.acl.transformers

import co.com.domain.model.entities.CurrencyAmount
import co.com.domain.model.types.Currency
import co.com.infrastructure.acl.dtos.CurrencyAmountDTO
import co.com.libs.error.AppError
import zio.IO

object CurrencyAmountTransformer {

  def toCurrencyAmount( dto: CurrencyAmountDTO ): IO[AppError, CurrencyAmount] = {
    Currency( dto.currency )
      .map( CurrencyAmount( _, BigDecimal( dto.amount ) ) )
  }

}
