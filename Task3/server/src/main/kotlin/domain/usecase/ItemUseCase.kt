package org.anware.domain.usecase

import org.anware.data.dto.item.ItemEditRequestBody
import org.anware.domain.entity.Item

interface ItemUseCase {
    fun add(warehouseId: Int, item: Item)
    fun getAllInWarehouse(warehouseId: Int): List<Item>
    fun getAllInSection(warehouseId: Int, sectionName: String): List<Item>
    fun getByRfidTag(warehouseId: Int, rfidTag: String) : Item
    fun deleteByRfidTag(warehouseId: Int, id: String)
    fun edit(warehouseId: Int, editRequestBody: ItemEditRequestBody)
}