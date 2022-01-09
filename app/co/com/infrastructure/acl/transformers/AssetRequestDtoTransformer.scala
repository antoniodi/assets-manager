package co.com.infrastructure.acl.transformers

import co.com.domain.model.entities.{ Asset, RealEstate }
import co.com.infrastructure.acl.dtos.{ AssetRequestDto, RealEstateRequestDto }
import co.com.infrastructure.config.Dependency
import co.com.libs.error.AppError
import zio.ZIO

trait AssetRequestDtoTransformer {

  def toAsset( assetId: String, dto: AssetRequestDto ): ZIO[Dependency, AppError, Asset] = {
    dto match {
      case realEstate: RealEstateRequestDto => toRealEstate( assetId, realEstate )
    }
  }

  def toRealEstate( id: String, dto: RealEstateRequestDto ): ZIO[Dependency, AppError, Asset] = {
    ZIO.accessZIO[Dependency] {
      _.currencyAmountDtoTransformer
        .toMoney( dto.cost.currency, dto.cost.amount )
        .map( RealEstate( id, dto.description, _, dto.address ) )
    }
  }

}

object AssetRequestDtoTransformer extends AssetRequestDtoTransformer