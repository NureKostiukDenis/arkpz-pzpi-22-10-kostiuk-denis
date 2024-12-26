package org.anware.data.service

import org.anware.data.dto.GateType
import org.anware.data.dto.SectionGateId
import org.anware.data.dto.SectionGateModel
import org.anware.data.repository.GateRepository
import org.anware.data.repository.SectionGateRepository
import org.anware.data.repository.SectionRepository
import org.anware.domain.entity.Gate
import org.anware.domain.entity.Section
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SectionGateService @Autowired constructor(
    private val sectionRepository: SectionRepository,
    private val sectionGateRepository: SectionGateRepository,
    private val gateRepository: GateRepository
)
{

    fun getType(sectionId: Int, gateId: Int): GateType? {
        return null
    }

    fun getSectionGates(): List<Gate>? {
        return null
    }

    fun delete(warehouseId: Int, gateCode: String, sectionName: String){
        val sectionModel = sectionName.let {
            sectionRepository.findByNameInWarehouse(it, warehouseId)
                ?: throw IllegalArgumentException("Section '$sectionName' not found in warehouse'.")
        }

        val gateModel = gateRepository.getByCodeInWarehouse(warehouseId, gateCode)
            ?: throw IllegalArgumentException("Gate with code '$gateCode' in warehouse not found.")

        val sectionGateId = SectionGateId(gateId = gateModel.id!!, sectionId = sectionModel.id!!)

        val sectionGate = sectionGateRepository.findById(sectionGateId)

        if (sectionGate.isEmpty){
            throw IllegalArgumentException("Gate does`nt exist")
        }

        sectionGateRepository.deleteById(sectionGateId)
    }

    fun save(warehouseId: Int, gateCode: String, sectionName: String, type: GateType){
        val sectionModel = sectionName.let {
            sectionRepository.findByNameInWarehouse(it, warehouseId)
                ?: throw IllegalArgumentException("Section '$sectionName' not found in warehouse'.")
        }

        val gateModel = gateRepository.getByCodeInWarehouse(warehouseId, gateCode)
            ?: throw IllegalArgumentException("Gate with code '$gateCode' in warehouse not found.")

        val sectionGateId = SectionGateId(gateId = gateModel.id!!, sectionId = sectionModel.id!!)

        val sectionGate = sectionGateRepository.findById(sectionGateId)

        if (sectionGate.isPresent){
            throw IllegalArgumentException("Exist")
        }

        val newSectionGate = SectionGateModel(
            id = SectionGateId(gateId = gateModel.id, sectionId = sectionModel.id),
            gate = gateModel,
            section = sectionModel,
            type = type
        )

        sectionGateRepository.save(newSectionGate)
    }


    @Transactional
    fun updateSectionForGate(warehouseId: Int, gateCode: String, newSectionName: String?, newGateType: GateType?) {
        val gate = gateRepository.getByCodeInWarehouse(warehouseId, gateCode)
            ?: throw IllegalArgumentException("Gate with code '$gateCode' in warehouse not found.")

        val newSection = newSectionName?.let {
            sectionRepository.findByNameInWarehouse(it, warehouseId)
                ?: throw IllegalArgumentException("Section '$newSectionName' not found in warehouse'.")
        }

        val currentSectionGate = sectionGateRepository.findByGate(gate.id!!)

        if (newSection != null) {
            val newSectionGate = SectionGateModel(
                id = SectionGateId(gateId = gate.id, sectionId = newSection.id!!),
                gate = gate,
                section = newSection,
                type = newGateType ?: sectionGateRepository.findBySectionAndGate(currentSectionGate!!.id!!, gate.id)!!.type
            )

            if (currentSectionGate != null) {
                sectionGateRepository.deleteById(SectionGateId(gateId = gate.id, sectionId = newSection.id))
            }

            sectionGateRepository.save(newSectionGate)
        }
    }

    fun getGatesSection(gateId: Int): Section? {
        val newSection = sectionGateRepository.findByGate(gateId)?.let {
            Section(
                id = it.id,
                name = it.name,
                capacity = it.capacity,
                warehouseId = it.warehouse.id!!
            )
        }

        return newSection
    }

    fun getGatesSection(warehouseId: Int, gateCode: String): Section? {
        val newSection = sectionGateRepository.findByGate(warehouseId, gateCode)?.let {
            Section(
                id = it.id,
                name = it.name,
                capacity = it.capacity,
                warehouseId = it.warehouse.id!!
            )
        }

        return newSection
    }
}