package co.com.domain.model.types

import co.com.libs.error.BusinessError

trait Currency {
  def code: String
  def name: String
}

object Currency {
  def apply( currencyCode: String ): Either[BusinessError, Currency] = currencyCode match {
    case "USD" => Right( USD )
    case "COP" => Right( COP )
    case "EUR" => Right( EUR )
    case _     => Left( BusinessError( s"The currency amount $currencyCode is not supported." ) )
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