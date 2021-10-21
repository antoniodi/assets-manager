package co.com.infrastructure.acl.transformers

import co.com.domain.model.entities.{ Asset, AssetId, RealEstate }
import co.com.infrastructure.acl.dtos.{ AssetDTO, CreateAssetDTO, CreateRealEstateDTO, RealEstateDTO }
import co.com.infrastructure.acl.transformers.CurrencyAmountTransformer.toCurrencyAmount
import co.com.libs.error.AppError
import zio.IO

object AssetDTOTransformer {

  def toAsset( dto: AssetDTO ): IO[AppError, Asset] = {
    dto match {
      case RealEstateDTO( id, assetType, description, cost, incomes, expenses, sellPrice ) => ???
      case _ => ???
    }
  }

  def toAsset( assetId: String, dto: CreateAssetDTO ): IO[AppError, Asset] = {
    val id = AssetId( assetId )
    dto match {
      case realEstate: CreateRealEstateDTO => toRealEstate( id, realEstate )
    }
  }

  def toRealEstate( id: AssetId, dto: CreateRealEstateDTO ): IO[AppError, Asset] = {
    toCurrencyAmount( dto.cost ).map( RealEstate( id, dto.description, _ ) )

  }

}
