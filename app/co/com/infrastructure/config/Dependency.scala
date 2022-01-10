package co.com.infrastructure.config

import co.com.application.ports.persistance.{ AssetRepositoryBase, ExpenseRepositoryBase, LiabilityRepositoryBase, UserRepositoryBase }
import co.com.application.services.{ AssetService, PersistenceUserService, ServiceHelper }
import co.com.domain.services.UserService
import co.com.infrastructure.acl.helpers.DateHelper
import co.com.infrastructure.acl.transformers.common.CurrencyAmountTransformer
import co.com.infrastructure.acl.transformers.{ AssetRequestDtoTransformer, ExpenseRequestDtoTransformer }
import co.com.infrastructure.controllers.commands.{ CreateUserZioCommandHelper, SaveAssetCommandHelper, SaveExpensesCommandHelper }
import co.com.infrastructure.persistence.dbConfigH2
import co.com.infrastructure.persistence.repositories.{ AssetRepository, ExpenseRepository, LiabilityRepository, UserRepository }
import co.com.infrastructure.services.{ RequestBanksService, RequestPostService }
import co.com.libs.command.core.{ CommandHandler, CommandHelper, DependencyBase }
import com.google.inject.Injector
import play.api.libs.ws.WSClient
import play.api.mvc.MessagesControllerComponents
import play.api.{ Configuration, Environment }
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import javax.inject.{ Inject, Singleton }

@Singleton
class Dependency @Inject() (
    val config: Configuration,
    val ws: WSClient,
    val injector: Injector,
    val environment: Environment
) extends DependencyBase {

  lazy val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigH2
  lazy val dbReadOnly: DatabaseConfig[JdbcProfile] = dbConfigH2

  //  lazy val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigPostgres
  //  lazy val dbReadOnly: DatabaseConfig[JdbcProfile] = dbConfigReadOnlyPostgres

  // Domain services
  lazy val userService: UserService = UserService

  // Application services
  lazy val assetService: AssetService = AssetService
  lazy val serviceHelper: ServiceHelper = ServiceHelper

  // Adapters
  lazy val requestBanksService: RequestBanksService = RequestBanksService
  lazy val requestPostService: RequestPostService = RequestPostService

  // Transformers
  lazy val currencyAmountDtoTransformer: CurrencyAmountTransformer = CurrencyAmountTransformer
  lazy val assetRequestDtoTransformer: AssetRequestDtoTransformer = AssetRequestDtoTransformer
  lazy val expenseRequestDtoTransformer: ExpenseRequestDtoTransformer = ExpenseRequestDtoTransformer

  // Infrastructure service
  lazy val savePersistenceUserService: PersistenceUserService = PersistenceUserService

  // Repositories
  lazy val userRepo: UserRepositoryBase = UserRepository
  lazy val expenseRepo: ExpenseRepositoryBase = ExpenseRepository
  lazy val assetRepo: AssetRepositoryBase = AssetRepository
  lazy val liabilityRepo: LiabilityRepositoryBase = LiabilityRepository

  // Helpers
  lazy val dateHelper: DateHelper = DateHelper

}

@Singleton
class CommandController @Inject() (
    dependency: Dependency,
    cc: MessagesControllerComponents
) extends CommandHandler( dependency, cc ) {

  val commandHelperList: List[CommandHelper[Dependency]] =
    List(
      new CreateUserZioCommandHelper(),
      new SaveExpensesCommandHelper(),
      new SaveAssetCommandHelper(),
      new SaveExpensesCommandHelper()
    )
}