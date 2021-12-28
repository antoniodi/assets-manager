package co.com.infrastructure.acl.http

import co.com.infrastructure.acl.dtos.HTTPError
import co.com.infrastructure.acl.formats.Formats.httpErrorWrite
import co.com.libs.error._
import org.slf4j
import play.api.Logger
import play.api.http.ContentTypes
import play.api.libs.json.Json
import play.api.mvc.{ Result, Results }

import java.time.{ Clock, LocalDateTime }

object ErrorHandler extends Results with ContentTypes {

  val logger: slf4j.Logger = Logger( getClass ).logger

  def handleHttpError( appError: AppError ): Result = {
    logger.error( appError.message, appError.error.orNull )

    val httpError = HTTPError( appError.errorType.toString, LocalDateTime.now( Clock.systemUTC() ), appError.message )
    val jsonHTTPError = Json.toJson( httpError )
    appError match {
      case _: BusinessErrorBase       => BadRequest( jsonHTTPError )
      case _: ApplicationErrorBase    => BadRequest( jsonHTTPError )
      case _: TransformationError     => BadRequest( jsonHTTPError )
      case _: InfrastructureErrorBase => InternalServerError( jsonHTTPError )
    }
  }

}
