package org.anware.data.dto.report

import java.time.LocalDate

data class PeakActivityHoursRequest(
    val warehouseId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
