package co.com.infrastructure.acl.http

import co.com.infrastructure.acl.formats.Formats._
import co.com.libs.error.AppError
import play.api.http.ContentTypes
import play.api.libs.json.Json
import play.api.mvc.Results

trait HTTPError extends Results with ContentTypes {

  def errorHttp( status: Status )( errors: AppError ) = {
    status.apply( Json.obj(
      "errors" -> Json.toJson( errors )
    ).toString ).as( JSON )
  }

  def badRequest = errorHttp( BadRequest )( _ )

  def internalServerError = errorHttp( InternalServerError )( _ )

  def notFound = errorHttp( NotFound )( _ )

  def failedDependency = errorHttp( FailedDependency )( _ )

}
