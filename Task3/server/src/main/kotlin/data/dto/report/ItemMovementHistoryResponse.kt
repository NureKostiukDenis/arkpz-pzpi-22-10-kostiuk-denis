package org.anware.data.dto.report

import java.time.LocalDateTime

data class ItemMovementHistoryResponse(
    val status: String,
    val itemRfidTag: String,
    val name: String,
    val total: Int,
    val movements: List<MovementDetail>
)

data class MovementDetail(
    val date: LocalDateTime,
    val from: Location,
    val to: Location
)

