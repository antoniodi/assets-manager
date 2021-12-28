package co.com.infrastructure.acl.dtos

sealed trait AssetRequestDto {
  def assetType: String

  def description: String
}

case class RealEstateRequestDto(
    assetType: String,
    description: String,
    cost: CurrencyAmountDto,
    address: String ) extends AssetRequestDto
