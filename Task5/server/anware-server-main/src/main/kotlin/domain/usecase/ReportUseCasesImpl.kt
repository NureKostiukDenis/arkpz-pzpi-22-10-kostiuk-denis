package org.anware.domain.usecase

import org.anware.data.dto.report.*
import org.anware.data.service.ReportService
import org.anware.data.service.WarehouseApiKeyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReportUseCasesImpl @Autowired constructor(
    private val reportService: ReportService,
    private val apiKeyService: WarehouseApiKeyService

) : ItemMovementHistoryUseCase,
    SectionLoadUseCase,
    ItemsWithoutMovementUseCase,
    PeakActivityHoursUseCase
{

    override fun getItemMovementHistory(apiKey: String, request: ItemMovementHistoryRequest): ItemMovementHistoryResponse {
        val warehouseData = apiKeyService.extractDataFromApiKey(apiKey)!!
        return reportService.getItemMovementHistory(warehouseData.warehouseId.toInt(), request)
    }

    override fun getSectionLoad(apiKey: String): SectionLoadResponse {
        val warehouseData = apiKeyService.extractDataFromApiKey(apiKey)!!
        return reportService.getSectionLoad(warehouseData.warehouseId.toInt(), warehouseData.warehouseName)
    }

    override fun getItemsWithoutMovement(apiKey: String, days: Int, size: Int): ItemsWithoutMovementResponse {
        val warehouseData = apiKeyService.extractDataFromApiKey(apiKey)!!
        return reportService.getItemsWithoutMovement(
            warehouseData.warehouseId.toInt(),
            warehouseData.warehouseName,
            days,
            size)
    }

    override fun getPeakActivityHours(apiKey: String, request: PeakActivityHoursRequest): PeakActivityResponse {
        val warehouseData = apiKeyService.extractDataFromApiKey(apiKey)!!
        return reportService.getPeakActivityHours(warehouseData.warehouseId.toInt(), warehouseData.warehouseName, request)
    }
}
