package co.com.factories

import co.com.infrastructure.acl.dtos.UserDTO

object UserDTOFactory {

  lazy val LouisUserDTO: UserDTO = UserDTO( "luiscocr", "email@gmail.com" )

}
