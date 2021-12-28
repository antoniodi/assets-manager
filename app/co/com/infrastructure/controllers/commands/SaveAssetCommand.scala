package co.com.infrastructure.controllers.commands

import co.com.infrastructure.acl.dtos.AssetRequestDto
import co.com.infrastructure.acl.formats.Formats.AssetFormat.assetReads
import co.com.infrastructure.acl.formats.Formats.doneWrite
import co.com.infrastructure.acl.http.ErrorHandler.handleHttpError
import co.com.infrastructure.config.Dependency
import co.com.libs.command.core.{ Command, CommandHelper }
import play.api.http.ContentTypes
import play.api.libs.json.{ JsPath, Json, Reads }
import play.api.mvc.{ Result, Results }
import zio.{ URIO, ZIO }

import java.util.UUID

case class SaveAssetCommand( dto: AssetRequestDto ) extends Command[Dependency] with Results with ContentTypes {

  def execute: URIO[Dependency, Result] = {
    val uid = UUID.randomUUID().toString
    //    val program: ZIO[Any, Throwable, Unit] = for {
    //      num         <- UIO( random )
    //      _           <- printLine("Guess a number between 1 and 3: ")
    //      guessString <- readLine
    //      _           <- ZIO.fromTry( Try( guessString.toInt ) ).orDie.foldZIO( ZIO.fail( _ ), guess => {
    //                        if (guess == num) printLine("You guessed right!")
    //                        else printLine(s"You guessed wrong, the number was $num")
    //                      } )
    //    } yield ()

    ZIO.accessZIO[Dependency] { dependency =>
      dependency.assetRequestDtoTransformer.toAsset( uid, dto )
        .flatMap( asset => dependency.assetService.saveAsset( asset ) )
    }.fold( {
      handleHttpError
    }, { result =>
      Ok( Json.toJson( result ) ).as( JSON )
    } )
  }
}

class SaveAssetCommandHelper extends CommandHelper[Dependency] {
  def name: String = "create-asset"

  def commandReads: Reads[Command[Dependency]] = JsPath.read[AssetRequestDto].map( SaveAssetCommand.apply )
}