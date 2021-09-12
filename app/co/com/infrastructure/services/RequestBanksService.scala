package co.com.infrastructure.services

import cats.effect.{ ContextShift, IO }
import co.com.application.acl.dtos.BankDTO

import scala.concurrent.Future

trait RequestBanksService {

  def getBanks()( implicit cs: ContextShift[IO] ): IO[List[BankDTO]] = {
    val banks = List(
      BankDTO( "ING", "cloud" ),
      BankDTO( "Santander", "Plaza de la Constitución, 6, 05110 El Barraco, Ávila, España" ) )

    IO.fromFuture { IO { Future.successful( banks ) } }
  }
}

object RequestBanksService extends RequestBanksService