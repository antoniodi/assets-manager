package co.com.application.services

import akka.Done
import co.com.domain.model.entities.Asset
import co.com.infrastructure.Types.ZIOS
import co.com.infrastructure.controllers.commands.Dependency

sealed trait AssetService {

  def saveAsset( asset: Asset ): ZIOS[Dependency, Done] = {
    ???
  }

}

object AssetService extends AssetService