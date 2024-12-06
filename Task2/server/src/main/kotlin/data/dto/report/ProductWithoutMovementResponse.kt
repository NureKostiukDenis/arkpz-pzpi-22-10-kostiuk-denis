package org.anware.data.dto.report

import java.time.LocalDate

data class ProductWithoutMovement(
    val status: String,
    val total: Int,
    val products: List<ProductInfo>
)

data class ProductInfo(
    val id: String,
    val name: String,
    val lastMovementDate: LocalDate,
    val location: Location
)
