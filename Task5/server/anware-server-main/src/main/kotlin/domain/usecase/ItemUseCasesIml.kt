package org.anware.domain.usecase

import org.anware.data.dto.item.AddItemRequest
import org.anware.data.dto.item.ItemEditRequestBody
import org.anware.data.service.ItemService
import org.anware.data.service.SectionService
import org.anware.data.service.WarehouseApiKeyService
import org.anware.domain.entity.Item
import org.anware.domain.handler.ApiKeyInvalidException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ItemUseCasesImpl(
    private val apiKeyService: WarehouseApiKeyService,
    private val itemService: ItemService,
    private val sectionService: SectionService
) : ItemUseCase {

    override fun add(apiKey: String, item: Item) {
        val warehouseId = apiKeyService.extractDataFromApiKey(apiKey)!!.warehouseId.toInt()
        if (item.warehouseId != warehouseId){
            throw ApiKeyInvalidException(null)
        }
        itemService.save(item)
    }

    override fun add(apiKey: String, item: AddItemRequest) {
        val warehouseId = apiKeyService.extractDataFromApiKey(apiKey)!!.warehouseId.toInt()
        val time = LocalDateTime.now()
        val itemData = Item(
            id = null,
            name = item.name,
            rfidTag = item.rfidTag,
            createdAt = time,
            updatedAt = time,
            warehouseId = warehouseId
        )
        add(apiKey, itemData)
    }

    override fun getAllInWarehouse(apiKey: String): List<Item> {
        val warehouseId = apiKeyService.extractDataFromApiKey(apiKey)!!.warehouseId.toInt()
        return itemService.findAll(warehouseId) ?: emptyList()
    }

    override fun getAllInSection(apiKey: String, sectionName: String): List<Item> {
        val warehouseId = apiKeyService.extractDataFromApiKey(apiKey)!!.warehouseId.toInt()
        sectionService.findByName(warehouseId, sectionName)
            ?: throw IllegalArgumentException("Section with name '$sectionName' in warehouse not found.")
        return itemService.findAll(warehouseId, sectionName) ?: emptyList()
    }

    override fun getByRfidTag(apiKey: String, rfidTag: String): Item? {
        val warehouseId = apiKeyService.extractDataFromApiKey(apiKey)!!.warehouseId.toInt()
        return itemService.findByRfidTag(warehouseId, rfidTag)
    }

    override fun deleteByRfidTag(apiKey: String, rfidTag: String) {
        val warehouseId = apiKeyService.extractDataFromApiKey(apiKey)!!.warehouseId.toInt()
        itemService.delete(warehouseId, rfidTag)
    }

    override fun edit(apiKey: String, rfidTag: String, editRequestBody: ItemEditRequestBody) {
        val warehouseId = apiKeyService.extractDataFromApiKey(apiKey)!!.warehouseId.toInt()
        val time = LocalDateTime.now()

        val itemData = Item(
            id = null,
            name = editRequestBody.name,
            rfidTag = editRequestBody.rfidTag,
            createdAt = time,
            updatedAt = time,
            warehouseId = warehouseId
        )

        itemService.edit(warehouseId, rfidTag, itemData)
    }

    override fun getAllItemWithoutSection(apiKey: String): List<Item> {
        val warehouseId = apiKeyService.extractDataFromApiKey(apiKey)!!.warehouseId.toInt()
        return itemService.findAllWithoutSection(warehouseId) ?: emptyList()
    }
}
