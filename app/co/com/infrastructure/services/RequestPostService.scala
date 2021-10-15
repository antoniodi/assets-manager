package co.com.infrastructure.services

import cats.data.Reader
import co.com.infrastructure.Types.EitherTResult
import co.com.infrastructure.acl.dtos.PostDTO
import co.com.infrastructure.controllers.commands.Dependency

trait RequestPostService {

  def requestPosts(): Reader[Dependency, EitherTResult[List[PostDTO]]] = Reader {
    dependencia: Dependency =>
      //      val url = "http://jsonplaceholder.typicode.com/posts"
      //
      //      EitherT[Task, NonEmptyList[ApplicationError], List[PostDTO]](
      //        Task.deferFuture {
      //          dependencia.ws.url( url ).get()
      //        }.map { wsResponse =>
      //          wsResponse.status match {
      //            case 200 => {
      //              wsResponse.json.validate[List[PostDTO]].fold( { errors =>
      //                Left( NonEmptyList.of( ConsumeServiceError( "Posts" ), DetailServiceError( s"For request ${wsResponse.toString} [Invalid Json: ${errors.toList.mkString( ", " )}]" ) ) )
      //              }, Right( _ ) )
      //            }
      //            case _ => Left( NonEmptyList.of( ConsumeServiceError( "Posts" ), DetailServiceError( s"${wsResponse.status}: ${wsResponse.statusText}." ) ) )
      //          }
      //        }.onErrorRecover {
      //          case error: Throwable => Left( NonEmptyList.of( ConsumeServiceError( "Posts" ), DetailServiceError( error.getMessage ) ) )
      //        }
      //      )
      ???
  }

}

object RequestPostService extends RequestPostService