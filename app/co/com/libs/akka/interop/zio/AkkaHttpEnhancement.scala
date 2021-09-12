package co.com.libs.akka.interop.zio

import play.api.mvc.{ Action, ActionBuilder, BodyParser, Result }
import zio.{ IO, Runtime, UIO }

object AkkaHttpEnhancement {

  val runtime: Runtime[zio.ZEnv] = Runtime.default

  implicit class ActionBuilderOps[+R[_], B]( actionBuilder: ActionBuilder[R, B] ) {

    def zio[E]( zioActionBody: R[B] => IO[E, Result] ): Action[B] = actionBuilder.async { request =>
      runtime.unsafeRunToFuture(
        ioToTask( zioActionBody( request ) )
      )
    }

    def zio[E, A]( bp: BodyParser[A] )( zioActionBody: R[A] => IO[E, Result] ): Action[A] = actionBuilder( bp ).async { request =>
      runtime.unsafeRunToFuture(
        ioToTask( zioActionBody( request ) )
      )
    }

    private def ioToTask[E, A]( io: IO[E, A] ) =
      io.mapError {
        case throwable: Throwable => throwable
        case string: String       => new Throwable( string )
        case error                => new Throwable( "Error: " + error.toString )
      }
  }

  implicit class RecoverIO[E, A]( io: IO[E, A] ) {
    def recover( f: E => A ): UIO[A] = io.fold( f, identity )
  }

}
