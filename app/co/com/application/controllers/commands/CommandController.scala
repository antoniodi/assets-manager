package co.com.application.controllers.commands

import co.com.application.contracts.UserRepositoryBase
import co.com.application.services.{ PersistenceUserService, ServiceHelper }
import co.com.domain.services.UserService
import co.com.infrastructure.persistence.dbConfigH2
import co.com.infrastructure.persistence.repositories.UserRepository
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
class CommandController @Inject() (
    dependency: Dependency,
    cc: MessagesControllerComponents
) extends CommandHandler( dependency, cc ) {

  val commandHelperList: List[CommandHelper] = List( new CreateUserZioCommandHelper() )
}

@Singleton
class Dependency @Inject() (
    val config: Configuration,
    val ws: WSClient,
    val injector: Injector,
    val environment: Environment
) extends DependencyBase {

  lazy val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigH2
  lazy val dbReadOnly: DatabaseConfig[JdbcProfile] = dbConfigH2

  lazy val userRepo: UserRepositoryBase = UserRepository

  lazy val serviceHelper: ServiceHelper = ServiceHelper
  lazy val savePersistenceUserService: PersistenceUserService = PersistenceUserService
  lazy val userService: UserService = UserService
  lazy val requestBanksService: RequestBanksService = RequestBanksService
  lazy val requestPostService: RequestPostService = RequestPostService
}
