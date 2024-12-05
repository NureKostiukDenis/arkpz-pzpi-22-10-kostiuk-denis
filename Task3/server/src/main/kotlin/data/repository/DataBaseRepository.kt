package org.anware.data.repository

import org.anware.data.dto.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface ItemRepository : JpaRepository<ItemModel, Int> {
    @Query("SELECT i FROM ItemModel i WHERE i.name = :name")
    fun findByName(@Param("name") name: String): List<ItemModel>

    @Query("SELECT i FROM ItemModel i WHERE i.rfidTag = :rfidTag")
    fun findByRfidTag(@Param("rfidTag") rfidTag: String): ItemModel?
}

interface WarehouseRepository : JpaRepository<WarehouseModel, Int> {
    @Query("SELECT w FROM WarehouseModel w WHERE w.name = :name")
    fun findByName(@Param("name") name: String): WarehouseModel?
}

interface SectionRepository : JpaRepository<WarehouseSectionModel, Int> {
    @Query("SELECT s FROM WarehouseSectionModel s WHERE s.name = :name")
    fun findByName(@Param("name") name: String): WarehouseSectionModel?

    @Query("SELECT s FROM WarehouseSectionModel s WHERE s.warehouse.id = :warehouseId")
    fun findAllByWarehouseId(@Param("warehouseId") warehouseId: Int): List<WarehouseSectionModel>
}

interface GateRepository : JpaRepository<GateModel, Int>

interface MovementsRepository : JpaRepository<MovementModel, Int> {
    @Query("SELECT m FROM MovementModel m WHERE m.itemLocation.id = :itemLocationId")
    fun findByItemLocationId(@Param("itemLocationId") itemLocationId: ItemLocationId): List<MovementModel>

    @Query("SELECT m FROM MovementModel m WHERE m.updatedAt > :dateTime")
    fun findByUpdatedAtAfter(@Param("dateTime") dateTime: LocalDateTime): List<MovementModel>
}

interface ItemLocationRepository : JpaRepository<ItemLocationsModel, Int> {
    @Query("SELECT il FROM ItemLocationsModel il WHERE il.item.id = :itemId")
    fun findByItemId(@Param("itemId") itemId: Int): List<ItemLocationsModel>

    @Query("SELECT il FROM ItemLocationsModel il WHERE il.section.id = :sectionId")
    fun findBySectionId(@Param("sectionId") sectionId: Int): List<ItemLocationsModel>
}
