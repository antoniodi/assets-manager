package co.com.application.services

import akka.Done
import co.com.application.controllers.commands.Dependency
import co.com.infrastructure.Types.ZIOS
import co.com.infrastructure.acl.dtos.UserDTO
import zio.ZIO

trait PersistenceUserService {

  def saveUser( userDTO: UserDTO ): ZIOS[Dependency, Done] = {
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
