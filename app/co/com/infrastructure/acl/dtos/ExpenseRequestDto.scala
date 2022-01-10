package co.com.infrastructure.acl.dtos

case class ExpenseRequestDto( category: String, value: CurrencyAmountDto, description: String, assetId: Option[String] = None, liabilityId: Option[String] = None )
