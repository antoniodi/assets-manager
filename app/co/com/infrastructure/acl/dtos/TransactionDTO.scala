package co.com.infrastructure.acl.dtos

import java.time.LocalDateTime

case class TransactionDTO(
    id: String,
    date: LocalDateTime,
    currencyAmount: CurrencyAmountDto,
    description: String )
