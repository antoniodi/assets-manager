package co.com.infrastructure.acl.dtos

trait CreateAssetDTO {
  def assetType: String

  def description: String
}

case class CreateRealEstateDTO(
    assetType: String,
    description: String,
    cost: CurrencyAmountDTO ) extends CreateAssetDTO
