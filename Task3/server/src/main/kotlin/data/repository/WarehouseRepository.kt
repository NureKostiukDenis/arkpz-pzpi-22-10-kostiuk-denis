package org.anware.data.repository

import org.anware.data.dto.WarehouseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface WarehouseRepository : JpaRepository<WarehouseModel, Int> {
    @Query("SELECT w FROM WarehouseModel w WHERE w.name = :name")
    fun findByName(@Param("name") name: String): WarehouseModel?
}