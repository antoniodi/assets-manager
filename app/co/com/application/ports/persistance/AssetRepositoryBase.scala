package co.com.application.ports.persistance

import co.com.domain.model.entities.{ Asset, User }
import co.com.infrastructure.Types.ZIOS
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

trait AssetRepositoryBase {

  def add( asset: Asset ): ZIOS[DatabaseConfig[JdbcProfile], Option[User]]

}
