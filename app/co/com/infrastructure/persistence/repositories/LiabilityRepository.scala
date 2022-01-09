package co.com.infrastructure.persistence.repositories

import co.com.application.ports.persistance.LiabilityRepositoryBase
import co.com.domain.model.entities.Liability
import co.com.infrastructure.persistence.tables.liabilities
import co.com.infrastructure.persistence.transformers.LiabilityTransformer.toLiability
import co.com.libs.error.{ AppError, DataBaseError }
import co.com.libs.slick.interop.zio.SlickEnhancement._
import co.com.libs.zio.enhancement.ZioEnhancement._
import org.slf4j
import play.api.Logger
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import zio.{ IO, ZIO }

object LiabilityRepository extends LiabilityRepositoryBase {

  val logger: slf4j.Logger = Logger( getClass ).logger

  def find( id: String ): ZIO[DatabaseConfig[JdbcProfile], AppError, Option[Liability]] = {
    val query = liabilities.filter( _.id === id )
    ZIO.fromDBIO( query.result )
      .refineOrDie( error => {
        val errorMessage = s"An error occurred trying to find the liability with id: [$id]."
        logger.error( errorMessage, error )
        DataBaseError( errorMessage )
      } )
      .flatMap( liabilities => IO.sequence( liabilities.headOption.map( toLiability ) ) )
  }
}
