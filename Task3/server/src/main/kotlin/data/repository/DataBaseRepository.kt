package org.anware.data.repository

import org.anware.data.dto.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface UserRepository : JpaRepository<UserModel, Int> {
    @Query("SELECT u FROM UserModel u WHERE u.uid = :uid")
    fun findByUid(@Param("uid") uid: String): UserModel?

    @Query("""
        SELECT uw.warehouse 
        FROM UserWarehouseModel uw 
        WHERE uw.user.uid = :uid
    """)
    fun findWarehouseByUserUid(@Param("uid") uid: String): WarehouseModel?
}

interface WarehouseRepository : JpaRepository<WarehouseModel, Int> {
    @Query("SELECT w FROM WarehouseModel w WHERE w.name = :name")
    fun findByName(@Param("name") name: String): WarehouseModel?
}

interface UserWarehouseRepository : JpaRepository<UserWarehouseModel, UserWarehouseId> {
    @Query("SELECT uw FROM UserWarehouseModel uw WHERE uw.user.id = :userId")
    fun findByUserId(@Param("userId") userId: Int): List<UserWarehouseModel>

    @Query("SELECT uw FROM UserWarehouseModel uw WHERE uw.warehouse.id = :warehouseId")
    fun findByWarehouseId(@Param("warehouseId") warehouseId: Int): List<UserWarehouseModel>
}

interface SectionRepository : JpaRepository<SectionModel, Int> {
    @Query("SELECT s FROM SectionModel s WHERE s.name = :name")
    fun findByName(@Param("name") name: String): SectionModel?

    @Query("SELECT s FROM SectionModel s WHERE s.warehouse.id = :warehouseId")
    fun findAllByWarehouseId(@Param("warehouseId") warehouseId: Int): List<SectionModel>
}

interface GateRepository : JpaRepository<GateModel, Int>

interface ItemRepository : JpaRepository<ItemModel, Int> {
    @Query("SELECT i FROM ItemModel i WHERE i.name = :name")
    fun findByName(@Param("name") name: String): List<ItemModel>

    @Query("SELECT i FROM ItemModel i WHERE i.rfidTag = :rfidTag")
    fun findByRfidTag(@Param("rfidTag") rfidTag: String): ItemModel?
}

interface ItemLocationRepository : JpaRepository<ItemLocationModel, ItemLocationId> {
    @Query("SELECT il FROM ItemLocationModel il WHERE il.item.id = :itemId")
    fun findByItemId(@Param("itemId") itemId: Int): List<ItemLocationModel>

    @Query("SELECT il FROM ItemLocationModel il WHERE il.section.id = :sectionId")
    fun findBySectionId(@Param("sectionId") sectionId: Int): List<ItemLocationModel>
}

interface MovementRepository : JpaRepository<MovementModel, Int> {
    @Query("SELECT m FROM MovementModel m WHERE m.item.id = :itemId AND m.section.id = :sectionId")
    fun findByItemAndSection(
        @Param("itemId") itemId: Int,
        @Param("sectionId") sectionId: Int
    ): List<MovementModel>

    @Query("SELECT m FROM MovementModel m WHERE m.updatedAt > :dateTime")
    fun findByUpdatedAtAfter(@Param("dateTime") dateTime: LocalDateTime): List<MovementModel>
}
