package co.com.application.controllers.queries

import co.com.application.acl.http.HTTPError
import co.com.application.controllers.commands.Dependency
import monix.execution.schedulers.ExecutorScheduler
import play.api.libs.json.Json
import play.api.mvc.{ MessagesAbstractController, MessagesControllerComponents }

import javax.inject.Inject
import co.com.application.acl.formats.Formats.postDTOReads

class RequestPosts @Inject() (
    dependency: Dependency,
    cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) with HTTPError {

  implicit val es: ExecutorScheduler = queryScheduler

  def request = Action.async { implicit request =>
    dependency.requestPostService.requestPosts().run( dependency )
      .fold( internalServerError, posts => Ok( Json.toJson( posts ) ) ).runToFuture
  }

}