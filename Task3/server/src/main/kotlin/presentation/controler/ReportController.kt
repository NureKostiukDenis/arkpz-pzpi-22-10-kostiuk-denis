package org.anware.presentation.controler

import org.anware.data.dto.WarehouseSectionRequest
import org.anware.data.dto.report.*
import org.anware.domain.usecase.ReportUseCases
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/{warehouseId}")
class ReportController @Autowired  constructor(
    private val reportUseCases: ReportUseCases
) {

    @GetMapping("/item-movement-history")
    fun getProductMovementHistory(@RequestBody body: ItemMovementHistoryRequest): ItemMovementHistoryResponse {
        TODO()
    }

    @GetMapping("/section-load")
    fun getSectionLoad(@RequestBody body: WarehouseSectionRequest): SectionLoadResponse {
        TODO()
    }

    @GetMapping("/items-without-movement")
    fun getProductsWithoutMovement(
        @RequestParam days: Int,
        @RequestParam size: Int
    ): ItemsWithoutMovementResponse {
        TODO()
    }

    @GetMapping("/peak-activity-hours")
    fun getPeakActivityHours(@RequestBody body: PeakActivityHoursRequest): PeakActivityResponse {
        TODO()
    }
}
