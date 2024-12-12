package org.anware.data.repository

import org.anware.data.dto.GateModel
import org.anware.data.dto.SectionGateId
import org.anware.data.dto.SectionGateModel
import org.anware.data.dto.SectionModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SectionGateRepository : JpaRepository<SectionGateModel, SectionGateId> {
    @Query("""
        SELECT sg FROM SectionGateModel sg 
        WHERE sg.section.id = :sectionId AND sg.gate.id = :gateId
    """)
    fun findBySectionAndGate(
        @Param("sectionId") sectionId: Int,
        @Param("gateId") gateId: Int
    ): SectionGateModel?


    @Query("""
        SELECT sg.section FROM SectionGateModel sg
        WHERE sg.gate.id = :gateId
    """)
    fun findByGate(
        @Param("gateId") gateId: Int,
    ): SectionModel?

    @Query("""
        SELECT sg.section FROM SectionGateModel sg
        WHERE sg.gate.code = :gateCode AND sg.section.warehouse.id = :warehouseId 
    """)
    fun findByGate(
        @Param("warehouseId") warehouseId: Int,
        @Param("gateCode") gateCode: String,
    ): SectionModel?

    @Query("""
        SELECT sg.gate FROM SectionGateModel sg
        WHERE sg.section.id = :sectionId
    """)
    fun getAllGatesInSection(
        @Param("sectionId") sectionId: Int
    ): List<SectionModel>?

    @Query("""
        SELECT sg.gate FROM SectionGateModel sg
        WHERE sg.section.name = :sectionName AND sg.section.warehouse.id = :warehouseId 
    """)
    fun getAllGatesInSection(
        @Param("warehouseId") warehouseId: Int,
        @Param("sectionName") sectionName: String
    ): List<GateModel>?
}
