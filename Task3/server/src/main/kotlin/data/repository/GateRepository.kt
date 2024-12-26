package org.anware.data.repository

import org.anware.data.dto.GateModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GateRepository : JpaRepository<GateModel, Int> {

    @Query("""
        SELECT g FROM GateModel g
        WHERE g.warehouse.id = :warehouseId AND g.code = :code
    """)
    fun getByCodeInWarehouse(
        @Param("warehouseId") warehouseId: Int,
        @Param("code") code: String
    ): GateModel?
}