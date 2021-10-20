package co.com.infrastructure.acl.dtos

trait AssetDTO {
  def id: String

  def assetType: String

  def description: String
}

case class RealEstateDTO(
    id: String,
    assetType: String,
    description: String,
    cost: CurrencyAmountDTO,
    incomes: List[TransactionDTO] = Nil,
    expenses: List[TransactionDTO] = Nil,
    sellPrice: Option[CurrencyAmountDTO] = None
) extends AssetDTO

