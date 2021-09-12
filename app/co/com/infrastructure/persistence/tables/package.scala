package co.com.infrastructure.persistence

import slick.lifted.TableQuery

package object tables {

  val users = TableQuery[UserTable]

}
