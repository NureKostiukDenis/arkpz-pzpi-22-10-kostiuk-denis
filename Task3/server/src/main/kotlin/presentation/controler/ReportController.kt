package org.anware.presentation.controler

import org.anware.data.dto.WarehouseSectionRequest
import org.anware.data.dto.report.*
import org.anware.domain.usecase.ReportUseCasesImpl
import org.anware.domain.usecase.UserUseCases
import org.anware.presentation.Headers.Companion.API_KEY_HEADER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/report")
class ReportController @Autowired constructor(
    private val reportUseCases: ReportUseCasesImpl,
    private val userUseCases: UserUseCases
) {

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    @GetMapping("/item-movement-history")
    fun getProductMovementHistory(
        @RequestHeader("ApiKey") apiKey: String,
        @RequestBody body: ItemMovementHistoryRequest
    ): ResponseEntity<ItemMovementHistoryResponse> {
        val report = reportUseCases.getItemMovementHistory(apiKey, body)
        return returnStatus(report, report.total)
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    @GetMapping("/section-load")
    fun getSectionLoad(
        @RequestHeader(API_KEY_HEADER) apiKey: String
    ): ResponseEntity<SectionLoadResponse> {
        val report = reportUseCases.getSectionLoad(apiKey)
        return returnStatus(report, report.total)
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    @GetMapping("/items-without-movement")
    fun getProductsWithoutMovement(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestParam days: Int,
        @RequestParam size: Int
    ): ResponseEntity<ItemsWithoutMovementResponse> {
        val report = reportUseCases.getItemsWithoutMovement(apiKey, days, size)
        return returnStatus(report, report.total)
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    @GetMapping("/peak-activity-hours")
    fun getPeakActivityHours(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestBody body: PeakActivityHoursRequest
    ): PeakActivityResponse {
        val report = reportUseCases.getPeakActivityHours(apiKey, body)
        return report
    }

    private fun <T> returnStatus(report: T, total: Int): ResponseEntity<T> {
        return if (total == 0) {
            ResponseEntity(report, HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(report, HttpStatus.OK)
        }
    }
}

