package co.com.infrastructure.acl.transformers

import co.com.infrastructure.acl.dtos.CurrencyAmountDto
import co.com.libs.error.TransformationError
import co.com.moneyContext
import squants.market.{ Currency, Money }
import zio.IO

import scala.util.{ Failure, Success }

trait CurrencyAmountDtoTransformer {

  def toMoney( dto: CurrencyAmountDto ): IO[TransformationError, Money] = {
    Currency( dto.currency )( moneyContext ) match {
      case Failure( exception ) => IO.fail( TransformationError( exception.getMessage, Some( exception ) ) )
      case Success( value )     => IO.succeed( Money( BigDecimal( dto.amount ), value ) )
    }
  }

}

object CurrencyAmountDtoTransformer extends CurrencyAmountDtoTransformer
