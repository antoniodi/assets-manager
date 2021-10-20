package co.com.application.ports.persistance

import co.com.domain.model.entities.{ Asset, User }
import co.com.libs.error.AppError
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import zio.ZIO

trait AssetRepositoryBase {

  def add( asset: Asset ): ZIO[DatabaseConfig[JdbcProfile], AppError, Option[User]]

}
