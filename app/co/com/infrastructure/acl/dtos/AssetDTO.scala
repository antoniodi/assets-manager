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
    cost: CurrencyAmountDto,
    incomes: List[TransactionDTO] = Nil,
    expenses: List[TransactionDTO] = Nil,
    sellPrice: Option[CurrencyAmountDto] = None
) extends AssetDTO

