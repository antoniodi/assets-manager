package co.com.infrastructure.acl.dtos

case class ExpenseRequestDto( category: String, currencyAmount: CurrencyAmountDto, description: String, assetId: Option[String] = None, liabilityId: Option[String] = None )
