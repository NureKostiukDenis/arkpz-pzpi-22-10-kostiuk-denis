package org.anware.data.dto.report

import java.time.LocalDateTime

data class PeakActivityHoursRequest(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)