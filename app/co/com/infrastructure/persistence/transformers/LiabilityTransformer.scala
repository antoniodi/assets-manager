package co.com.infrastructure.persistence.transformers

import co.com.domain.model.entities.{ Liability, Mortgage }
import co.com.infrastructure.acl.transformers.common.CurrencyAmountTransformer
import co.com.infrastructure.persistence.tables.LiabilityRow
import co.com.libs.error.TransformationError
import zio.IO

object LiabilityTransformer {

  def toLiability( liability: LiabilityRow ): IO[TransformationError, Liability] = {
    for {
      validTotalDebt <- CurrencyAmountTransformer.toMoney( liability.totalDebtCurrency, liability.totalDebtAmount )
      validLiability <- liability.`type` match {
        case ""            => IO.succeed( Mortgage( liability.id, liability.id, validTotalDebt ) )
        case liabilityType => IO.fail( TransformationError( s"The liabilityType: [$liabilityType] is not supported yet." ) )
      }
    } yield validLiability
  }

}
