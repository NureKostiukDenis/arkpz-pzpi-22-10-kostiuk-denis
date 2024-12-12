package org.anware.data.dto.report

import java.time.LocalDate

data class PeakActivityResponse(
    val status: String,
    val warehouse: String,
    val total: Int,
    val dailyActivities: List<DailyActivity>
)

data class DailyActivity(
    val day: LocalDate,
    val peakHours: List<PeakHours>
)

data class PeakHours(
    val hour: Int,
    val activityCount: Long
)
