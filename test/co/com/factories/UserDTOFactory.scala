package co.com.factories

import co.com.application.acl.dtos.UserDTO

object UserDTOFactory {

  lazy val LouisUserDTO: UserDTO = UserDTO( "luiscocr", "email@gmail.com" )

}
