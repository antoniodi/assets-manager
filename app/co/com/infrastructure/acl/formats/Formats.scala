package co.com.infrastructure.acl.formats

import akka.Done
import co.com.domain.model.entities.User
import co.com.infrastructure.acl.dtos.{ AssetDTO, BankDTO, CreateAssetDTO, CreateRealEstateDTO, CurrencyAmountDTO, PostDTO, RealEstateDTO, TransactionDTO, UserDTO }
import co.com.infrastructure.acl.http.HTTPError
import co.com.libs.error.{ AppError, ApplicationError, BusinessError, InfrastructureError }
import play.api.libs.json._

object Formats {

  implicit val appErrorFormat: Writes[AppError] = {
    case businessError: BusinessError             => Json.writes[BusinessError].writes( businessError )
    case applicationError: ApplicationError       => Json.writes[ApplicationError].writes( applicationError )
    case infrastructureError: InfrastructureError => Json.writes[InfrastructureError].writes( infrastructureError )
  }

  implicit val httpErrorWrite: Writes[HTTPError] = Json.writes[HTTPError]

  implicit val doneWrite: Writes[Done] = ( o: Done ) =>
    Json.obj( "result" -> "OK" )

  implicit val userDTOWrite: Writes[User] = Json.writes[User]
  implicit val userDTOReads: Reads[UserDTO] = Json.reads[UserDTO]
  implicit val postDTOReads: OFormat[PostDTO] = Json.format[PostDTO]
  implicit val bankDTOWrite: Writes[BankDTO] = Json.writes[BankDTO]

  implicit val currencyAmountDTOFormat: OFormat[CurrencyAmountDTO] = Json.format[CurrencyAmountDTO]
  implicit val transactionWrite: Writes[TransactionDTO] = Json.writes[TransactionDTO]

  object AssetFormat {

    implicit val assetWrites: Writes[AssetDTO] = {
      case realEstate: RealEstateDTO => Json.writes[RealEstateDTO].writes( realEstate )
    }

    implicit val assetReads: Reads[CreateAssetDTO] = ( json: JsValue ) => {
      ( json \ "assetType" ).as[String] match {
        case "RealEstate" => json.validate[CreateRealEstateDTO]( Json.reads[CreateRealEstateDTO] )
        case assetType    => JsError( s"The assetType: $assetType is not supported yet." )
      }
    }

    //    implicit val assetFormat: Format[AssetDTO] = new Format[AssetDTO] {
    //      def reads(json: JsValue): JsResult[AssetDTO] = {
    //        (json \ "assetType").as[String] match {
    //          case "RealEstate" => json.validate[RealEstateDTO]
    //          case _ =>
    //        }
    //      }
    //
    //      def writes(o: AssetDTO): JsValue = {
    //        case realEstate: RealEstateDTO => Json.writes[RealEstateDTO].writes(realEstate)
    //      }
    //    }

  }

}
