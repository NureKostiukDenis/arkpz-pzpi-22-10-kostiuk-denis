package org.anware.presentation.controler

import org.anware.data.dto.WarehouseSectionRequest
import org.anware.data.dto.report.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/report")
class ReportService {

    @GetMapping("/product-movement-history")
    fun getProductMovementHistory(@RequestBody body: ProductMovementHistoryRequest): ProductMovementHistoryResponse {
        TODO()
    }

    @GetMapping("/section-load")
    fun getSectionLoad(@RequestBody body: WarehouseSectionRequest): SectionLoadResponse {
        TODO()
    }

    @GetMapping("/products-without-movement")
    fun getProductsWithoutMovement(
        @RequestParam days: Int,
        @RequestParam size: Int
    ): ProductWithoutMovement {
        TODO()
    }

    @GetMapping("/peak-activity-hours")
    fun getPeakActivityHours(@RequestBody body: PeakActivityHoursRequest): PeakActivityResponse {
        TODO()
    }
}
