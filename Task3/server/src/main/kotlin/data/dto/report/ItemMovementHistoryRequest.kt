package org.anware.data.dto.report

import java.time.LocalDate

data class ItemMovementHistoryRequest(
    val itemRfidTag: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)