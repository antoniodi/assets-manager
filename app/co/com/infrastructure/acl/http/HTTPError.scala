package co.com.infrastructure.acl.http

import java.time.LocalDateTime

case class HTTPError( errorType: String, date: LocalDateTime, errorMessage: String )

