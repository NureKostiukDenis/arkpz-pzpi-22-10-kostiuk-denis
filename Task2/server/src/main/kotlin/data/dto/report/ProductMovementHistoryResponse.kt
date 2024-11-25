package org.anware.data.dto.report

import java.time.LocalDateTime

data class ProductMovementHistoryResponse(
    val status: String,
    val productId: String,
    val name: String,
    val total: Int,
    val movements: List<MovementDetail>
)

data class MovementDetail(
    val date: LocalDateTime,
    val from: Location,
    val to: Location
)

