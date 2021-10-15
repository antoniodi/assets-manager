package co.com.infrastructure.controllers.queries

import co.com.infrastructure.acl.http.HTTPError
import co.com.infrastructure.controllers.commands.Dependency
import co.com.libs.akka.interop.zio.AkkaHttpEnhancement._
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents, Request}
import zio.ZIO

import javax.inject.Inject

class VersionQuery @Inject() ( dependency: Dependency, cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) with HTTPError {

  def version: Action[AnyContent] = Action.zio { implicit request: Request[AnyContent] =>
    ZIO.succeed( Ok( "version" ) )
  }

}

