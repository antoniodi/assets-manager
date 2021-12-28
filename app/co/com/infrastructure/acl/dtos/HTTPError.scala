package co.com.infrastructure.acl.dtos

import java.time.LocalDateTime

case class HTTPError( errorType: String, date: LocalDateTime, errorMessage: String )
