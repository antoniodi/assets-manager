package co.com.application.services

import java.time.LocalDateTime

trait ServiceHelper {

  def getCurrentLocalDateTime(): LocalDateTime = LocalDateTime.now()

}

object ServiceHelper extends ServiceHelper
