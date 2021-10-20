package co.com.infrastructure.acl.transformers

import co.com.domain.model.entities.Asset
import co.com.infrastructure.acl.dtos.{ AssetDTO, CreateAssetDTO, CreateRealEstateDTO, RealEstateDTO }
import co.com.libs.error.AppError
import zio.IO

object AssetDTOTransformer {

  def toAsset( dto: AssetDTO ): IO[AppError, Asset] = {
    dto match {
      case RealEstateDTO( id, assetType, description, cost, incomes, expenses, sellPrice ) => ???
      case _ => ???
    }
  }

  def toAsset( dto: CreateAssetDTO ): IO[AppError, Asset] = {
    dto match {
      case CreateRealEstateDTO( assetType, description, cost ) => ???
      case _ => ???
    }
  }

}
