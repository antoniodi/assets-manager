package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

class GetExpenseSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://localhost:9003/ms-base")
    .contentTypeHeader( "application/json" )

  val scn: ScenarioBuilder = scenario("Find expense")
    .exec(
      http("request_1")
        .get( "/expenses/e-1" )
        .check( status.is(200 ) )
        .check( jsonPath("$.id").is("e-1") )
    ).pause(1)

  setUp(
    scn.inject( rampUsers(1 ) during (11 seconds) )
  ).protocols(httpProtocol)
}
