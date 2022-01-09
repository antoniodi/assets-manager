package co.com.application.ports.persistance

import co.com.domain.model.entities.Asset
import co.com.libs.error.AppError
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import zio.ZIO

trait AssetRepositoryBase {

  def save( asset: Asset ): ZIO[DatabaseConfig[JdbcProfile], AppError, String]

  def find( id: String ): ZIO[DatabaseConfig[JdbcProfile], AppError, Option[Asset]]

}
