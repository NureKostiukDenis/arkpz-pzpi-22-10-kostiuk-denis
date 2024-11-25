package org.anware.data.dto.report

import java.time.LocalDate

data class ProductMovementHistoryRequest(
    val productId: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)