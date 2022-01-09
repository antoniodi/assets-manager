package co.com.infrastructure.persistence.repositories

import co.com.application.ports.persistance.AssetRepositoryBase
import co.com.domain.model.entities.{ Asset, LoanToThirdParty }
import co.com.infrastructure.persistence.repositories.LiabilityRepository.logger
import co.com.infrastructure.persistence.tables.asset
import co.com.infrastructure.persistence.transformers.LiabilityTransformer.toLiability
import co.com.libs.error.{ AppError, DataBaseError }
import co.com.libs.slick.interop.zio.SlickEnhancement._
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import zio.ZIO

object AssetRepository extends AssetRepositoryBase {

  def save( asset: Asset ): ZIO[DatabaseConfig[JdbcProfile], AppError, String] = ???

  def find( id: String ): ZIO[DatabaseConfig[JdbcProfile], AppError, Option[Asset]] = {
    val query = asset.filter( _.id === id )
    ZIO.fromDBIO( query.result )
      .refineOrDie( error => {
        val errorMessage = s"An error occurred trying to find the liability with id: [$id]."
        logger.error( errorMessage, error )
        DataBaseError( errorMessage )
      } )
      .map( _.headOption.map( asset => LoanToThirdParty( asset.id, asset.description ) ) )
  }
}
