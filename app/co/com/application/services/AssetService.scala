package co.com.application.services

import akka.Done
import co.com.domain.model.entities.Asset
import co.com.infrastructure.config.Dependency
import co.com.libs.error.AppError
import zio.ZIO

sealed trait AssetService {

  def saveAsset( asset: Asset ): ZIO[Dependency, AppError, Done] = {
    ???
  }

}

object AssetService extends AssetService