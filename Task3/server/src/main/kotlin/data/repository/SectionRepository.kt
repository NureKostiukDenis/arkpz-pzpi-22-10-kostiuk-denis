package org.anware.data.repository

import org.anware.data.dto.SectionModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SectionRepository : JpaRepository<SectionModel, Int> {
    @Query("SELECT s FROM SectionModel s WHERE s.name = :name AND s.warehouse.id = :warehouseId")
    fun findByNameInWarehouse(@Param("name") name: String, @Param("warehouseId") warehouseId: Int): SectionModel?

    @Query("SELECT s FROM SectionModel s WHERE s.warehouse.id = :warehouseId")
    fun findAllByWarehouseId(@Param("warehouseId") warehouseId: Int): List<SectionModel>?

    @Query("""
        SELECT COUNT(i) FROM ItemModel i
        JOIN ItemLocationModel il ON il.item.id = i.id
        JOIN SectionModel s ON s.id = il.section.id
        WHERE i.warehouse.id = :warehouseId AND s.name = :sectionName
    """)
    fun getCurrentLoadInSection(
        @Param("warehouseId") warehouseId: Int,
        @Param("sectionName") sectionName: String
    ): Int

    @Query("""
    SELECT il.section FROM ItemLocationModel il
    WHERE il.item.rfidTag = :rfidTag AND il.section.warehouse.id = :warehouseId
""")
    fun findByItemRfidTagInWarehouse(
        @Param("warehouseId") warehouseId: Int,
        @Param("rfidTag") rfidTag: String
    ): SectionModel?
}