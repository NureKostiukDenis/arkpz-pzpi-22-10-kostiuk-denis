package org.anware.data.dto.report

data class PeakActivityResponse(
    val status: String,
    val warehouse: String,
    val warehouseId: Int,
    val total: Int,
    val peakHours: List<PeakHour>
)

data class PeakHour(
    val timePeriod: String,
    val activityCount: Int
)