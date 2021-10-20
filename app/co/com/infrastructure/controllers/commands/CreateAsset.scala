package co.com.infrastructure.controllers.commands

import co.com.infrastructure.acl.dtos.CreateAssetDTO
import co.com.infrastructure.acl.formats.Formats.doneWrite
import co.com.infrastructure.acl.formats.Formats.AssetFormat.assetReads
import co.com.infrastructure.acl.http.ErrorHandler.handleHttpError
import co.com.infrastructure.acl.transformers.AssetDTOTransformer.toAsset
import co.com.libs.command.core.{ Command, CommandHelper }
import play.api.http.ContentTypes
import play.api.libs.json.{ JsPath, Json, Reads }
import play.api.mvc.{ Result, Results }
import zio.{ URIO, ZIO }

case class CreateAssetCommand( dto: CreateAssetDTO ) extends Command[Dependency] with Results with ContentTypes {

  def execute: URIO[Dependency, Result] = {
    toAsset( dto ).flatMap { asset =>
      ZIO.accessZIO[Dependency]( _.assetService.saveAsset( asset ) )
    }.fold( {
      handleHttpError
    }, { result =>
      Ok( Json.toJson( result ) ).as( JSON )
    } )
  }
}

class CreateAssetCommandHelper extends CommandHelper[Dependency] {
  def name: String = "create-asset"

  def commandReads: Reads[Command[Dependency]] = JsPath.read[CreateAssetDTO].map( CreateAssetCommand.apply )
}