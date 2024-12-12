package org.anware.domain.usecase

import org.anware.data.dto.GateType
import org.anware.data.dto.gate.AddGateRequest
import org.anware.data.dto.gate.AttachGateToSectionRequest
import org.anware.data.dto.gate.DetachGateFromSectionRequest
import org.anware.data.dto.gate.EditGateRequest
import org.anware.data.service.*
import org.anware.domain.entity.Gate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GateUseCasesImpl @Autowired constructor(
    private val gateService: GateService,
    private val sectionGateService: SectionGateService,
    private val apiKeyService: WarehouseApiKeyService
) : GateUseCases {

    override fun add(apiKey: String, gate: AddGateRequest) {
        val warehouseId = getWarehouseId(apiKey)
        val newGate = Gate(
            id = null,
            code = gate.code,
            warehouseId = warehouseId
        )
        gateService.save(warehouseId, newGate)
    }

    override fun delete(apiKey: String, gateCode: String) {
        val warehouseId = getWarehouseId(apiKey)
        gateService.delete(warehouseId, gateCode)
    }

    override fun edit(apiKey: String, code: String, newGateData: EditGateRequest) {
        val warehouseId = getWarehouseId(apiKey)
        val newGate = Gate(
            id = null,
            code = newGateData.code,
            warehouseId =warehouseId
        )
        gateService.edit(warehouseId, code, newGate)
    }

    override fun attachToSection(apiKey: String, attachData: AttachGateToSectionRequest) {
        val warehouseId = getWarehouseId(apiKey)

        val type = GateType.getType(attachData.connectionType)
            ?: throw IllegalArgumentException("")

        sectionGateService.save(warehouseId, attachData.gateCode, attachData.sectionName, type)
    }

    override fun detachFromSection(apiKey: String, detachData: DetachGateFromSectionRequest) {
        val warehouseId = getWarehouseId(apiKey)

        sectionGateService.delete(warehouseId, detachData.gateCode, detachData.sectionName)
    }


    private fun getWarehouseId(apiKey: String): Int{
        val data = apiKeyService.extractDataFromApiKey(apiKey)
        return data!!.warehouseId.toInt()
    }

}