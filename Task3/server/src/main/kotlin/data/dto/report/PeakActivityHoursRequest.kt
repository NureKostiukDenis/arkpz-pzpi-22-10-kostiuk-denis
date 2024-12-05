package org.anware.data.dto.report

import java.time.LocalDate

data class PeakActivityHoursRequest(
    val startDate: LocalDate,
    val endDate: LocalDate,
)
