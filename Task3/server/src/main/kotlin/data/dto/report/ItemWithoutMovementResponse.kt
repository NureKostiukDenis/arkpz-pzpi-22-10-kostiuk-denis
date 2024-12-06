package org.anware.data.dto.report

import java.time.LocalDate

data class ItemsWithoutMovementResponse(
    val status: String,
    val total: Int,
    val items: List<ItemInfo>
)

data class ItemInfo(
    val id: String,
    val name: String,
    val lastMovementDate: LocalDate,
    val location: Location
)
