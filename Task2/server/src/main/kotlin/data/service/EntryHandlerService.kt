package org.anware.data.service

import org.anware.domain.handler.ApiKeyInvalidException
import org.anware.domain.handler.MqttMessageHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EntryHandlerService @Autowired constructor(
    private val itemService: ItemService,
    private val movementService: MovementService,
    private val sectionGateService: SectionGateService,
    private val gateApiKeyService: GateApiKeyService,
    private val userService: UserService
) : MqttMessageHandler, Logger() {

    override fun handleMessage(topic: String, payload: String, gateApiKey: String?, gateCode: String?) {
        if (gateApiKey == null) {
            throw ApiKeyInvalidException("Invalid ApiKey")
        }

        if (gateCode == null){
            throw IllegalArgumentException("Invalid gate code")
        }

        val gateApiKeyData = gateApiKeyService.extractDataFromApiKey(gateApiKey)
            ?: throw ApiKeyInvalidException("Invalid ApiKey")

        val warehouseId = gateApiKeyData.warehouseId.toInt()
        val userWarehouseId = userService.findWarehouseByUserUID(gateApiKeyData.userUID)?.id
            ?: throw ApiKeyInvalidException("Invalid ApiKey")

        if (warehouseId != userWarehouseId){
            throw ApiKeyInvalidException("Invalid ApiKey")
        }

        val item = itemService.findByRfidTag(warehouseId, payload)

        if (item == null){
            logger.warn("No item in database with rfidTag $payload")
            return
        }

        val section = sectionGateService.getGatesSection(warehouseId, gateCode)

        if (section == null){
            logger.warn("No section connected to gate in database with gate code $gateCode")
            return
        }

        movementService.moveItem(warehouseId, item.rfidTag, section.name)
    }
}