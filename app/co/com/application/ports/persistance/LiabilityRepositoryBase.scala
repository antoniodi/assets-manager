package co.com.application.ports.persistance

import co.com.domain.model.entities.Liability
import co.com.libs.error.AppError
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import zio.ZIO

trait LiabilityRepositoryBase {

  def find( id: String ): ZIO[DatabaseConfig[JdbcProfile], AppError, Option[Liability]]

}
