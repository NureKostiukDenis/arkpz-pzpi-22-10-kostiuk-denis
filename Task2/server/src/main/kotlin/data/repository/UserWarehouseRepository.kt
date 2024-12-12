package org.anware.data.repository

import org.anware.data.dto.UserWarehouseId
import org.anware.data.dto.UserWarehouseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserWarehouseRepository : JpaRepository<UserWarehouseModel, UserWarehouseId> {
    @Query("SELECT uw FROM UserWarehouseModel uw WHERE uw.user.id = :userId")
    fun findByUserId(@Param("userId") userId: Int): List<UserWarehouseModel>

    @Query("SELECT uw FROM UserWarehouseModel uw WHERE uw.warehouse.id = :warehouseId")
    fun findByWarehouseId(@Param("warehouseId") warehouseId: Int): List<UserWarehouseModel>
}