package co.com.infrastructure.acl.transformers.common

import co.com.libs.error.TransformationError
import co.com.moneyContext
import squants.market.{ Currency, Money }
import zio.IO

import scala.util.{ Failure, Success }

trait CurrencyAmountTransformer {

  def toMoney( currency: String, amount: Double ): IO[TransformationError, Money] = {
    Currency( currency )( moneyContext ) match {
      case Success( value )     => IO.succeed( Money( BigDecimal( amount ), value ) )
      case Failure( exception ) => IO.fail( TransformationError( exception.getMessage, Some( exception ) ) )
    }
  }

}

object CurrencyAmountTransformer extends CurrencyAmountTransformer
