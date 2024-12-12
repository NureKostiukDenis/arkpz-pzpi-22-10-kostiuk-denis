package org.anware.data.dto.report

import java.time.LocalDate
import java.time.LocalDateTime

data class ItemsWithoutMovementResponse(
    val status: String,
    val total: Int,
    val items: List<ItemInfo>
)

data class ItemInfo(
    val name: String,
    val lastMovementDate: LocalDateTime,
    val location: Location
)
