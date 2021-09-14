package co.com.application.services

import akka.Done
import cats.data.{ EitherT, Reader }
import co.com.infrastructure.acl.dtos.UserDTO
import co.com.application.controllers.commands.Dependency
import co.com.infrastructure.Types.{ EitherTResult, ZIOS }
import monix.eval.Task
import zio.ZIO

trait PersistenceUserService {

  def saveUser( userDTO: UserDTO ): ZIOS[Dependency, Done] = {
    for {
      dependency <- ZIO.environment[Dependency]
      user <- dependency.userRepo.findWithZio( userDTO.username ).provide( dependency.dbReadOnly )
      _ <- dependency.userService.validateExistingUser( user )
      _ <- dependency.userService.validateExistingUser( user )
      userId = dependency.userRepo.generateId()
      newUser = UserDTO.toUser( userId, userDTO )
      currentDate = dependency.serviceHelper.getCurrentLocalDateTime()
      _ <- dependency.userRepo.addZio( newUser, currentDate ).provide( dependency.dbConfig )
    } yield Done
  }

}

object PersistenceUserService extends PersistenceUserService
