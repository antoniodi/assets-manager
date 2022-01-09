package co.com.infrastructure.acl.formats

import akka.Done
import co.com.domain.model.entities.{ Expense, User }
import co.com.infrastructure.acl.dtos.{ HTTPError, _ }
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import squants.market.Money

object Formats {

  implicit val httpErrorWrite: Writes[HTTPError] = Json.writes[HTTPError]

  implicit val doneWrite: Writes[Done] = ( o: Done ) =>
    Json.obj( "result" -> o.toString )

  implicit val userDTOWrite: Writes[User] = Json.writes[User]
  implicit val userDTOReads: Reads[UserDTO] = Json.reads[UserDTO]
  implicit val postDTOReads: Format[PostDTO] = Json.format[PostDTO]
  implicit val bankDTOWrite: Writes[BankDTO] = Json.writes[BankDTO]

  implicit val currencyAmountDTOReads: Reads[CurrencyAmountDto] = {
    (
      ( JsPath \ "currency" ).read[String] and
      ( JsPath \ "amount" ).read[Double]( min( 0d ) )
    )( CurrencyAmountDto.apply _ )
  }

  implicit val currencyAmountDTOWrite: Writes[CurrencyAmountDto] = Json.writes[CurrencyAmountDto]
  implicit val transactionWrite: Writes[TransactionDTO] = Json.writes[TransactionDTO]

  implicit val moneyWrites: Writes[Money] = ( money: Money ) => {
    Json.obj(
      "currency" -> money.currency.code,
      "amount" -> money.amount
    )
  }

  object AssetFormat {

    implicit val assetWrites: Writes[AssetDTO] = {
      case realEstate: RealEstateDTO => Json.writes[RealEstateDTO].writes( realEstate )
    }

    implicit val assetReads: Reads[AssetRequestDto] = ( json: JsValue ) => {
      ( json \ "assetType" ).asOpt[String] match {
        case Some( "RealEstate" ) => json.validate[RealEstateRequestDto]( Json.reads[RealEstateRequestDto] )
        case Some( assetType )    => JsError( s"The assetType: [$assetType] is not supported yet." )
        case None                 => JsError( JsonValidationError( s"Invalid Json: [assetType] path is missing." ) )
      }
    }
  }

  object TransactionFormat {
    implicit val expenseFormat: Reads[ExpenseRequestDto] = Json.reads[ExpenseRequestDto]

    implicit def writesExpenses: Writes[List[Expense]] = Writes[List[Expense]] {
      expenses =>
        Json.obj(
          "expenses" -> expenses.map { expense =>
            Json.obj(
              "id" -> expense.id,
              "date" -> expense.date,
              "category" ->
                Json.obj(
                  "code" -> expense.category.code,
                  "description" -> expense.category.description,
                ),
              "description" -> expense.description,
              "value" -> expense.value
            )
          }
        )
    }
  }

}
