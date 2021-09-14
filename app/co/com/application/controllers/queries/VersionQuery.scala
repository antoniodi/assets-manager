package co.com.application.controllers.queries

import co.com.application.controllers.commands.Dependency
import co.com.infrastructure.acl.http.HTTPError
import co.com.libs.akka.interop.zio.AkkaHttpEnhancement._
import play.api.mvc.{ AnyContent, MessagesAbstractController, MessagesControllerComponents, Request }
import zio.ZIO

import javax.inject.Inject

class VersionQuery @Inject() ( dependency: Dependency, cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) with HTTPError {

  def version = Action.zio { implicit request: Request[AnyContent] =>
    val version = Ok( "version" )
    ZIO.succeed( version )
  }

}

