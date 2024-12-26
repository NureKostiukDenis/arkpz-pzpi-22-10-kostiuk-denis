package org.anware.data.repository

import jakarta.persistence.Tuple
import org.anware.data.dto.ItemModel
import org.anware.data.dto.MovementModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface MovementRepository : JpaRepository<MovementModel, Int> {

    @Query(
        """
    SELECT m.updatedAt 
    FROM MovementModel m
    WHERE m.newSection.warehouse.id = :warehouseId AND m.item.id = :itemId
    ORDER BY m.updatedAt DESC
    """
    )
    fun getLastMovementDates(
        @Param("warehouseId") warehouseId: Int,
        @Param("itemId") itemId: Int
    ): List<LocalDateTime>



    @Query("""
    SELECT m FROM MovementModel m
    WHERE m.item.id = :itemId AND m.updatedAt BETWEEN :startDate AND :endDate
""")
    fun findMovementsForItemWithinDateRange(
        @Param("itemId") itemId: Int,
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime
    ): List<MovementModel>?

    @Query("""
    SELECT m FROM MovementModel m
    WHERE m.item.id = :itemId
""")
    fun findMovementsForItem(
        @Param("itemId") itemId: Int
    ): List<MovementModel>?



    @Query("""
        SELECT i FROM ItemModel i
        WHERE NOT EXISTS (
            SELECT 1 FROM MovementModel m 
            WHERE m.item.id = i.id AND m.updatedAt > :dateThreshold
        ) AND i.warehouse.id = :warehouseId
""")
    fun findItemsWithoutMovementSince(
        @Param("warehouseId") warehouseId: Int,
        @Param("dateThreshold") dateThreshold: LocalDateTime
    ): List<ItemModel>?

//    @Query(
//        value = """
//    SELECT DATE(m.updated_at) AS day, COUNT(m.id) AS activityCount
//    FROM movement m
//    JOIN section s ON m.new_section_id = s.id
//    WHERE s.warehouse_id = :warehouseId
//      AND m.updated_at BETWEEN :startDate AND :endDate
//    GROUP BY DATE(m.updated_at)
//    ORDER BY activityCount DESC
//    """,
//        nativeQuery = true
//    )
//    fun findDailyActivityByWarehouseAndDateRange(
//        @Param("warehouseId") warehouseId: Int,
//        @Param("startDate") startDate: LocalDateTime,
//        @Param("endDate") endDate: LocalDateTime
//    ): List<Tuple>?
//
//    @Query(
//        value = """
//    SELECT HOUR(m.updated_at) AS hour, COUNT(m.id) AS activityCount
//    FROM movement m
//    JOIN section s ON m.new_section_id = s.id
//    WHERE s.warehouse_id = :warehouseId
//      AND m.updated_at BETWEEN :startDate AND :endDate
//    GROUP BY HOUR(m.updated_at)
//    ORDER BY activityCount DESC
//    """,
//        nativeQuery = true
//    )
//    fun findPeakActivityByWarehouseAndDateRange(
//        @Param("warehouseId") warehouseId: Int,
//        @Param("startDate") startDate: LocalDateTime,
//        @Param("endDate") endDate: LocalDateTime
//    ): List<Tuple>?

    @Query(
        value = """
    SELECT DATE(m.updated_at) AS day, 
           HOUR(m.updated_at) AS hour, 
           COUNT(m.id) AS activityCount
    FROM movement m
    JOIN section s ON m.new_section_id = s.id
    WHERE s.warehouse_id = :warehouseId
      AND m.updated_at BETWEEN :startDate AND :endDate
    GROUP BY DATE(m.updated_at), HOUR(m.updated_at)
    ORDER BY day, hour
    """,
        nativeQuery = true
    )
    fun findDetailedActivityByWarehouseAndDateRange(
        @Param("warehouseId") warehouseId: Int,
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime
    ): List<Tuple>?
}