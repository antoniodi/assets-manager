package co.com.infrastructure.controllers.queries

import co.com.infrastructure.controllers.commands.Dependency
import monix.execution.schedulers.ExecutorScheduler
import play.api.mvc.{ Action, AnyContent, MessagesAbstractController, MessagesControllerComponents }

import javax.inject.Inject

class RequestPosts @Inject() (
    dependency: Dependency,
    cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) {

  implicit val es: ExecutorScheduler = queryScheduler

  def request: Action[AnyContent] = Action.async { implicit request =>
    //      dependency.requestPostService.requestPosts().run( dependency )
    //        .fold( internalServerError, posts => Ok( Json.toJson( posts ) ) ).runToFuture
    ???
  }

}