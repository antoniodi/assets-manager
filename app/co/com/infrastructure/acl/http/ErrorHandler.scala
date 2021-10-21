package co.com.infrastructure.acl.http

import co.com.infrastructure.acl.formats.Formats.httpErrorWrite
import co.com.libs.error.{ AppError, ApplicationError, BusinessError, InfrastructureError, TransformationError }
import play.api.http.ContentTypes
import play.api.libs.json.Json
import play.api.mvc.{ Result, Results }

import java.time.{ Clock, LocalDateTime }

object ErrorHandler extends Results with ContentTypes {

  def handleHttpError( appError: AppError ): Result = {
    val jsonHTTPError = Json.toJson( HTTPError( appError.errorType.toString, LocalDateTime.now( Clock.systemUTC() ), appError.message ) )
    appError match {
      case _: BusinessError       => BadRequest( jsonHTTPError )
      case _: ApplicationError    => BadRequest( jsonHTTPError )
      case _: TransformationError => BadRequest( jsonHTTPError )
      case _: InfrastructureError => InternalServerError( jsonHTTPError )
    }
  }

}
