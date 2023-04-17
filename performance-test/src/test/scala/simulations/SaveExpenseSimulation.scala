package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

class SaveExpenseSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://localhost:9000/ms-base")
    .contentTypeHeader( "application/json" )

  val body: String =
    """
      |{
      |    "category": "ENTERTAINMENT",
      |    "value": {
      |        "currency": "COP",
      |        "amount": 123
      |    },
      |    "description": "description"
      |}""".stripMargin

  val scn: ScenarioBuilder = scenario("Save expense")
    .exec(
      http("request_1")
        .post("/command/save-expense")
        .body( StringBody( body ) )
        .check( status.is(201 ) )
    ).pause(1)

  setUp(
    scn.inject( rampUsers(1) during (1 seconds) )
  ).protocols(httpProtocol)
}
