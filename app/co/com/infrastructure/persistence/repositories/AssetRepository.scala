package co.com.infrastructure.persistence.repositories

import co.com.application.ports.persistance.AssetRepositoryBase
import co.com.domain.model.entities.{ Asset, User }
import co.com.libs.error.AppError
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import zio.ZIO

object AssetRepository extends AssetRepositoryBase {
  def add( asset: Asset ): ZIO[DatabaseConfig[JdbcProfile], AppError, Option[User]] = ???
}
