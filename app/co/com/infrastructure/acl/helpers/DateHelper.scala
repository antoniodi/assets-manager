package co.com.infrastructure.acl.helpers

import java.time.{ Clock, LocalDateTime }

trait DateHelper {

  def getNow: LocalDateTime = LocalDateTime.now( Clock.systemUTC() )

}

object DateHelper extends DateHelper