package co.com.application.services

import akka.Done
import co.com.infrastructure.acl.dtos.UserDTO
import co.com.infrastructure.config.Dependency
import co.com.libs.error.AppError
import zio.ZIO

trait PersistenceUserService {

  def saveUser( userDTO: UserDTO ): ZIO[Dependency, AppError, Done] = {
    for {
      dependency <- ZIO.environment[Dependency]
      user <- dependency.userRepo.findWithZio( userDTO.username ).provide( dependency.dbReadOnly )
      _ <- dependency.userService.validateExistingUser( user )
      userId = dependency.userRepo.generateId()
      newUser = UserDTO.toUser( userId, userDTO )
      currentDate = dependency.serviceHelper.getCurrentLocalDateTime()
      result <- dependency.userRepo.addZio( newUser, currentDate ).provide( dependency.dbConfig )
    } yield result
  }

}

object PersistenceUserService extends PersistenceUserService
