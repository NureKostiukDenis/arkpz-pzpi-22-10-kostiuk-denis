package org.anware.domain.usecase

import org.anware.data.dto.report.*

interface ItemMovementHistoryUseCase {
    fun getItemMovementHistory(apiKey: String, request: ItemMovementHistoryRequest): ItemMovementHistoryResponse
}

interface SectionLoadUseCase {
    fun getSectionLoad(apiKey: String): SectionLoadResponse
}

interface ItemsWithoutMovementUseCase {
    fun getItemsWithoutMovement(apiKey: String, days: Int, size: Int): ItemsWithoutMovementResponse
}

interface PeakActivityHoursUseCase {
    fun getPeakActivityHours(apiKey: String, request: PeakActivityHoursRequest): PeakActivityResponse
}
