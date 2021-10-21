package co.com.infrastructure.acl.formats

import akka.Done
import co.com.domain.model.entities.User
import co.com.infrastructure.acl.dtos._
import co.com.infrastructure.acl.http.HTTPError
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

object Formats {

  implicit val httpErrorWrite: Writes[HTTPError] = Json.writes[HTTPError]

  implicit val doneWrite: Writes[Done] = ( o: Done ) =>
    Json.obj( "result" -> o.toString )

  implicit val userDTOWrite: Writes[User] = Json.writes[User]
  implicit val userDTOReads: Reads[UserDTO] = Json.reads[UserDTO]
  implicit val postDTOReads: Format[PostDTO] = Json.format[PostDTO]
  implicit val bankDTOWrite: Writes[BankDTO] = Json.writes[BankDTO]

  implicit val currencyAmountDTOReads: Reads[CurrencyAmountDTO] = {
    (
      ( JsPath \ "currency" ).read[String] and
      ( JsPath \ "amount" ).read[Double]( min( 0d ) )
    )( CurrencyAmountDTO.apply _ )
  }

  implicit val currencyAmountDTOWrite: Writes[CurrencyAmountDTO] = Json.writes[CurrencyAmountDTO]
  implicit val transactionWrite: Writes[TransactionDTO] = Json.writes[TransactionDTO]

  object AssetFormat {

    implicit val assetWrites: Writes[AssetDTO] = {
      case realEstate: RealEstateDTO => Json.writes[RealEstateDTO].writes( realEstate )
    }

    implicit val assetReads: Reads[CreateAssetDTO] = ( json: JsValue ) => {
      ( json \ "assetType" ).asOpt[String] match {
        case Some( "RealEstate" ) => json.validate[CreateRealEstateDTO]( Json.reads[CreateRealEstateDTO] )
        case Some( assetType )    => JsError( s"The assetType: [$assetType] is not supported yet." )
        case None                 => JsError( JsonValidationError( s"Invalid Json: [assetType] path is missing." ) )
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
