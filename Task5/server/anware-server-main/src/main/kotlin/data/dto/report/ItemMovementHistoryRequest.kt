package org.anware.data.dto.report

import java.time.LocalDateTime

data class ItemMovementHistoryRequest(
    val itemRfidTag: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)