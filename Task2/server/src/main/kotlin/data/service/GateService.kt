package org.anware.data.service

import org.anware.data.dto.GateModel
import org.anware.data.repository.GateRepository
import org.anware.data.repository.SectionRepository
import org.anware.data.repository.WarehouseRepository
import org.anware.domain.entity.Gate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class GateService @Autowired constructor(
    private val gateRepository: GateRepository,
    private val warehouseRepository: WarehouseRepository
) {

    fun save(warehouseId: Int, gate: Gate){
        val existGate = gateRepository.getByCodeInWarehouse(warehouseId, gate.code)
        val warehouse = warehouseRepository.getReferenceById(warehouseId)

        if (existGate != null){
            throw Exception("Gate with code ${gate.code} exists")
        }

        val newGate = GateModel(
            code = gate.code,
            warehouse = warehouse
        )

        gateRepository.save(newGate)
    }

    fun delete(warehouseId: Int, gateCode: String) {
        val existGate = gateRepository.getByCodeInWarehouse(warehouseId, gateCode)
            ?: throw IllegalArgumentException("Gate with code $gateCode does not exist in warehouse $warehouseId")

        gateRepository.delete(existGate)
    }

    fun edit(warehouseId: Int, gateCode: String, newGate: Gate) {
        val existingGate = gateRepository.getByCodeInWarehouse(warehouseId, gateCode)
            ?: throw IllegalArgumentException("Gate with code $gateCode does not exist in warehouse $warehouseId")

        val updatedGate = existingGate.copy(
            id =  existingGate.id,
            code = newGate.code
        )

        gateRepository.save(updatedGate)
    }

    fun find(warehouseId: Int, code: String): Gate? {
        val gateModel = gateRepository.getByCodeInWarehouse(warehouseId, code)
            ?: return null

        val gate = Gate(
            id = gateModel.id,
            code = gateModel.code,
            warehouseId = warehouseId
        )

        return gate
    }

}