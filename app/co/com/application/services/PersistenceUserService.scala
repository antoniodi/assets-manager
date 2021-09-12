package co.com.application.services

import akka.Done
import cats.data.{ EitherT, Reader }
import co.com.application.acl.dtos.UserDTO
import co.com.application.controllers.commands.Dependency
import co.com.infrastructure.Types.EitherTResult
import monix.eval.Task

trait PersistenceUserService {

  def saveUser( userDTO: UserDTO ): Reader[Dependency, EitherTResult[Done]] = Reader {
    dependency: Dependency =>

      for {
        user <- dependency.userRepo.findWithTask( userDTO.username ).run( dependency.dbReadOnly )
        _ <- EitherT( Task( dependency.userService.validateExistingUser( user ) ) )
        userId = dependency.userRepo.generateId()
        newUser = UserDTO.toUser( userId, userDTO )
        currentDate = dependency.serviceHelper.getCurrentLocalDateTime()
        _ <- dependency.userRepo.add( newUser, currentDate ).run( dependency.dbConfig )
      } yield Done

  }

}

object PersistenceUserService extends PersistenceUserService
