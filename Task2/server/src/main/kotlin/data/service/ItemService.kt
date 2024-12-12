package org.anware.data.service

import org.anware.data.dto.ItemModel
import org.anware.data.repository.ItemRepository
import org.anware.data.repository.WarehouseRepository
import org.anware.domain.entity.Item
import org.anware.domain.entity.toItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService @Autowired constructor(
    private val itemRepository: ItemRepository,
    private val warehouseRepository: WarehouseRepository
) {

    fun find(id: Int): ItemModel? {
        return itemRepository.findById(id).orElse(null)
    }

    fun findAll(warehouseId: Int, sectionName: String): List<Item>? {
        return itemRepository.findAll(warehouseId, sectionName)?.map { it.toItem() }
    }

    fun findAll(warehouseId: Int): List<Item>? {
        return itemRepository.findAllinWarehouse(warehouseId)?.map { it.toItem() }
    }

    fun save(item: Item) {
        val warehouse = warehouseRepository.getReferenceById(item.warehouseId)

        val itemModel = ItemModel(
            id = item.id,
            name = item.name,
            rfidTag = item.rfidTag,
            createdAt = item.createdAt,
            updatedAt = item.updatedAt,
            warehouse = warehouse
        )

        itemRepository.save(itemModel)
    }

    fun delete(warehouseId: Int, rfidTag: String){
        itemRepository.deleteByRfidTag(rfidTag, warehouseId)
    }

    fun findByRfidTag(warehouseId: Int, rfidTag: String): Item?{
        return itemRepository.findByRfidTag(rfidTag, warehouseId)?.toItem()
    }

    fun edit(warehouseId: Int, rfidTag: String, newItem: Item) {
        val existingItem = itemRepository.findByRfidTag(rfidTag, warehouseId)
            ?: throw IllegalArgumentException("Item with RFID tag $rfidTag does not exist in warehouse")

        val warehouse = warehouseRepository.findById(warehouseId)
            .orElseThrow { IllegalArgumentException("Warehouse does not exist") }

        val updatedItem = existingItem.copy(
            name = newItem.name,
            rfidTag = newItem.rfidTag,
            updatedAt = newItem.updatedAt,
            warehouse = warehouse
        )

        itemRepository.save(updatedItem)
    }

    fun findAllWithoutSection(warehouseId: Int): List<Item>?{
        return itemRepository.findAllWithoutSection(warehouseId)?.map { it.toItem() }
    }

}