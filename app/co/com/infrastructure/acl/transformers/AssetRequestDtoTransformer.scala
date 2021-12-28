package co.com.infrastructure.acl.transformers

import co.com.domain.model.entities.{ Asset, AssetId, RealEstate }
import co.com.infrastructure.acl.dtos.{ AssetRequestDto, RealEstateRequestDto }
import co.com.infrastructure.config.Dependency
import co.com.libs.error.AppError
import zio.ZIO

trait AssetRequestDtoTransformer {

  def toAsset( assetId: String, dto: AssetRequestDto ): ZIO[Dependency, AppError, Asset] = {
    val id = AssetId( assetId )
    dto match {
      case realEstate: RealEstateRequestDto => toRealEstate( id, realEstate )
    }
  }

  def toRealEstate( id: AssetId, dto: RealEstateRequestDto ): ZIO[Dependency, AppError, Asset] = {
    ZIO.accessZIO[Dependency] {
      _.currencyAmountDtoTransformer
        .toMoney( dto.cost )
        .map( RealEstate( id, dto.description, _, dto.address ) )
    }
  }

}

object AssetRequestDtoTransformer extends AssetRequestDtoTransformer