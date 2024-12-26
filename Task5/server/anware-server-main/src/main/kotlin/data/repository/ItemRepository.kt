package org.anware.data.repository

import org.anware.data.dto.ItemModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemRepository : JpaRepository<ItemModel, Int> {

    @Query("SELECT i FROM ItemModel i WHERE i.rfidTag = :rfidTag AND i.warehouse.id = :warehouseId")
    fun findByRfidTag(
        @Param("rfidTag") rfidTag: String,
        @Param("warehouseId") warehouseId: Int
    ): ItemModel?

    @Query("DELETE FROM ItemModel i WHERE i.rfidTag = :rfidTag AND i.warehouse.id = :warehouseId")
    fun deleteByRfidTag(
        @Param("rfidTag") rfidTag: String,
        @Param("warehouseId") warehouseId: Int
    ): Int

    @Query("""
        SELECT i FROM ItemModel i
        JOIN ItemLocationModel il ON il.item.id = i.id
        JOIN SectionModel s ON s.id = il.section.id
        WHERE i.warehouse.id = :warehouseId AND s.name = :sectionName
    """)
    fun findAll(
        @Param("warehouseId") warehouseId: Int,
        @Param("sectionName") sectionName: String
    ): List<ItemModel>?

    @Query("SELECT i FROM ItemModel i WHERE i.warehouse.id = :warehouseId")
    fun findAllinWarehouse(
        @Param("warehouseId") warehouseId: Int
    ): List<ItemModel>?

    @Query("""
        SELECT i FROM ItemModel i
        LEFT JOIN ItemLocationModel il ON il.item.id = i.id
        WHERE i.warehouse.id = :warehouseId AND il.section.id IS NULL
    """)
    fun findAllWithoutSection(
        @Param("warehouseId") warehouseId: Int
    ): List<ItemModel>?
}