package co.com.infrastructure.services

import cats.data.{ EitherT, NonEmptyList, Reader }
import co.com.application.acl.dtos.PostDTO
import co.com.application.controllers.commands.Dependency
import co.com.infrastructure.Types.EitherTResult
import co.com.application.acl.formats.Formats.postDTOReads
import co.com.suite.error.{ ApplicationError, ConsumeServiceError, DetailServiceError }
import monix.eval.Task

trait RequestPostService {

  def requestPosts(): Reader[Dependency, EitherTResult[List[PostDTO]]] = Reader {
    dependencia: Dependency =>
      val url = "http://jsonplaceholder.typicode.com/posts"

      EitherT[Task, NonEmptyList[ApplicationError], List[PostDTO]](
        Task.deferFuture {
          dependencia.ws.url( url ).get()
        }.map { wsResponse =>
          wsResponse.status match {
            case 200 => {
              wsResponse.json.validate[List[PostDTO]].fold( { errors =>
                Left( NonEmptyList.of( ConsumeServiceError( "Posts" ), DetailServiceError( s"For request ${wsResponse.toString} [Invalid Json: ${errors.toList.mkString( ", " )}]" ) ) )
              }, Right( _ ) )
            }
            case _ => Left( NonEmptyList.of( ConsumeServiceError( "Posts" ), DetailServiceError( s"${wsResponse.status}: ${wsResponse.statusText}." ) ) )
          }
        }.onErrorRecover {
          case error: Throwable => Left( NonEmptyList.of( ConsumeServiceError( "Posts" ), DetailServiceError( error.getMessage ) ) )
        }
      )
  }

}

object RequestPostService extends RequestPostService