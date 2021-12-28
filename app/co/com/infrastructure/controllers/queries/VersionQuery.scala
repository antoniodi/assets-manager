package co.com.infrastructure.controllers.queries

import co.com.infrastructure.config.Dependency
import co.com.libs.akka.interop.zio.AkkaHttpEnhancement._
import play.api.mvc._
import zio.ZIO

import javax.inject.Inject

class VersionQuery @Inject() ( dependency: Dependency, cc: MessagesControllerComponents ) extends MessagesAbstractController( cc ) {

  def version: Action[AnyContent] = Action.zio { implicit request: Request[AnyContent] =>
    ZIO.succeed( Ok( "version" ) )
  }

}

