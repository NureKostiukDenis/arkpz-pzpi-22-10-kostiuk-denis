package org.anware.data.service

import org.anware.data.dto.report.*
import org.anware.data.repository.ItemRepository
import org.anware.data.repository.MovementRepository
import org.anware.data.repository.SectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReportService @Autowired constructor(
    private val itemRepository: ItemRepository,
    private val movementRepository: MovementRepository,
    private val sectionRepository: SectionRepository
): Logger() {

    fun getItemMovementHistory(warehouseId: Int, request: ItemMovementHistoryRequest): ItemMovementHistoryResponse {
        val item = itemRepository.findByRfidTag(request.itemRfidTag, warehouseId = warehouseId)
            ?: throw IllegalArgumentException("Item with RFID ${request.itemRfidTag} not found")

        val movements = movementRepository.findMovementsForItem(
            itemId = item.id!!,
        )?.map { movement ->
            MovementDetail(
                date = movement.updatedAt,
                from = Location(
                    warehouseName = movement.oldSection.warehouse.name ?: "Unknown",
                    sectionTitle = movement.oldSection.name
                ),
                to = Location(
                    warehouseName = movement.newSection.warehouse.name ?: "Unknown",
                    sectionTitle = movement.newSection.name
                )
            )
        }

        return ItemMovementHistoryResponse(
            status = "successful",
            itemRfidTag = request.itemRfidTag,
            name = item.name ?: "Unnamed Item",
            total = movements?.size ?: 0 ,
            movements = movements ?: emptyList()
        )
    }

    fun getSectionLoad(warehouseId: Int, warehouseName: String): SectionLoadResponse {
        val sections = sectionRepository.findAllByWarehouseId(warehouseId)
            ?: throw IllegalArgumentException("Sections don`t exist for this warehouse")

            val sectionsData = sections.map { section ->
            SectionInfo(
                name = section.name,
                currentLoad = sectionRepository.getCurrentLoadInSection(warehouseId, section.name),
                capacity = section.capacity
            )
        }

        return SectionLoadResponse(
            warehouseName = warehouseName,
            status = "successful",
            sections = sectionsData,
            total = sectionsData.size
        )
    }

    fun getItemsWithoutMovement(warehouseId: Int, warehouseName: String, days: Int, size: Int): ItemsWithoutMovementResponse {
        val dateThreshold = LocalDateTime.now().minusDays(days.toLong())
        val items = movementRepository.findItemsWithoutMovementSince(warehouseId, dateThreshold)?.map { item ->
            logger.info(item.name)
            ItemInfo(
                name = item.name !!,
                lastMovementDate = movementRepository.getLastMovementDates(warehouseId, item.id!!).firstOrNull() ?: LocalDateTime.MIN,
                location = Location(
                    warehouseName = warehouseName,
                    sectionTitle = sectionRepository.findByItemRfidTagInWarehouse(warehouseId, item.rfidTag)?.name ?: "No attached"
                )
            )
        }

        return ItemsWithoutMovementResponse(
            status = "successful",
            total = items?.size ?: 0,
            items = items ?: emptyList()
        )
    }

    fun getPeakActivityHours(
        warehouseId: Int,
        warehouseName: String,
        request: PeakActivityHoursRequest
    ): PeakActivityResponse {
        val peakData = movementRepository.findDetailedActivityByWarehouseAndDateRange(
            startDate = request.startDate,
            endDate = request.endDate,
            warehouseId = warehouseId
        )

        val dailyActivities = peakData?.groupBy {
            it.get("day", java.sql.Date::class.java)?.toLocalDate()
        }?.mapNotNull { (day, tuples) ->
            if (day != null) {
                val peakHours = tuples.mapNotNull { tuple ->
                    val hour = tuple.get("hour", Integer::class.java)?.toInt()
                    val activityCount = tuple.get("activityCount", Number::class.java)?.toLong()
                    if (hour != null && activityCount != null) {
                        PeakHours(hour, activityCount)
                    } else {
                        null
                    }
                }
                DailyActivity(day, peakHours)
            } else {
                null
            }
        } ?: emptyList()

        return PeakActivityResponse(
            status = "successful",
            warehouse = warehouseName,
            total = dailyActivities.size,
            dailyActivities = dailyActivities
        )
    }

}