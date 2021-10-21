package co.com.domain.model.types

import co.com.libs.error.BusinessError
import zio.IO

trait Currency {
  def code: String
  def name: String
}

object Currency {
  def apply( currencyCode: String ): IO[BusinessError, Currency] = currencyCode match {
    case "USD" => IO.succeed( USD )
    case "COP" => IO.succeed( COP )
    case "EUR" => IO.succeed( EUR )
    case _     => IO.fail( BusinessError( s"The currency [$currencyCode] is not supported yet." ) )
  }
}

case object USD extends Currency {
  def code: String = "USD"
  def name: String = "US DOLLAR"
}

case object COP extends Currency {
  def code: String = "COP"
  def name: String = "COLOMBIAN PESO"
}

case object EUR extends Currency {
  def code: String = "EUR"
  def name: String = "EURO"
}