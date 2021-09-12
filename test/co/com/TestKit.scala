package co.com

import co.com.application.controllers.commands.Dependency
import co.com.application.services.{ PersistenceUserService, ServiceHelper }
import co.com.domain.contracts.UserRepositoryBase
import co.com.domain.services.UserService
import co.com.infrastructure.persistence.repositories.configFileTest
import co.com.infrastructure.services.RequestPostService
import com.google.inject.Injector
import monix.execution.ExecutionModel.AlwaysAsyncExecution
import monix.execution.schedulers.ExecutorScheduler
import monix.execution.{ Features, UncaughtExceptionReporter }
import org.scalamock.scalatest.MockFactory
import org.scalatest.EitherValues
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient
import play.api.{ Application, Configuration, Environment }
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import java.util.concurrent.{ Executors, SynchronousQueue, ThreadPoolExecutor, TimeUnit }
import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

trait TestKitBase extends ScalaFutures with Matchers with EitherValues with MockFactory

abstract class TestKit extends AnyWordSpec {

  implicit val executionContextTest: ExecutionContext = ExecutionContext.fromExecutorService( Executors.newFixedThreadPool( 5 ) )

  lazy val executor = new ThreadPoolExecutor( 0, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue[Runnable] )

  implicit val executionSchedulerTest: ExecutorScheduler = ExecutorScheduler(
    executor,
    UncaughtExceptionReporter( t => println( s"this should not happen: ${t.getMessage}" ) ),
    AlwaysAsyncExecution,
    Features.empty
  )

  final private val appBuilder = new GuiceApplicationBuilder()

  def testApp: Application = appBuilder.configure( Configuration( configFileTest ) ).overrides(
    bind[Dependency].to[FalseConfigurations]
  ).build()

  def getInstanceFalseConfigurations: FalseConfigurations = new FalseConfigurations( null, null, null )

}

@Singleton
class FalseConfigurations @Inject() (
    wsClient: WSClient,
    injector: Injector,
    environment: Environment
) extends Dependency( Configuration( configFileTest ), wsClient, injector, environment ) with TestKitBase {

  implicit val executionContext: ExecutionContext = ExecutionContext.Implicits.global

  override lazy val dbConfig: DatabaseConfig[JdbcProfile] = mock[DatabaseConfig[JdbcProfile]]
  override lazy val dbReadOnly: DatabaseConfig[JdbcProfile] = mock[DatabaseConfig[JdbcProfile]]

  override lazy val userRepo: UserRepositoryBase = mock[UserRepositoryBase]

  override lazy val serviceHelper: ServiceHelper = mock[ServiceHelper]
  override lazy val savePersistenceUserService: PersistenceUserService = mock[PersistenceUserService]
  override lazy val userService: UserService = mock[UserService]
  override lazy val requestPostService: RequestPostService = mock[RequestPostService]

}
