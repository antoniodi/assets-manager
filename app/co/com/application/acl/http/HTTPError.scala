package co.com.application.acl.http

import cats.data.NonEmptyList
import co.com.application.acl.formats.Formats._
import co.com.suite.error.ApplicationError
import play.api.http.ContentTypes
import play.api.libs.json.Json
import play.api.mvc.Results

trait HTTPError extends Results with ContentTypes {

  def errorHttp( status: Status )( errors: NonEmptyList[ApplicationError] ) = {
    status.apply( Json.obj(
      "errors" -> Json.toJson( errors.toList )
    ).toString ).as( JSON )
  }

  def badRequest = errorHttp( BadRequest )( _ )

  def internalServerError = errorHttp( InternalServerError )( _ )

  def notFound = errorHttp( NotFound )( _ )

  def failedDependency = errorHttp( FailedDependency )( _ )

}
