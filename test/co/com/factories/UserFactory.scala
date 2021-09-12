package co.com.factories

import co.com.domain.model.entities.User

object UserFactory {

  lazy val LouisUser: User = User( "a-1", "luiscocr", "email@gmail.com" )

}
