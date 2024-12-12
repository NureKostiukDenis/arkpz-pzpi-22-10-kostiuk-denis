package org.anware.domain.usecase

import org.anware.data.dto.item.AddItemRequest
import org.anware.data.dto.item.ItemEditRequestBody
import org.anware.domain.entity.Item

interface ItemUseCase {
    fun add(apiKey: String, item: Item)
    fun add(apiKey: String, item: AddItemRequest)
    fun getAllInWarehouse(apiKey: String): List<Item>
    fun getAllInSection(apiKey: String, sectionName: String): List<Item>
    fun getByRfidTag(apiKey: String, rfidTag: String): Item?
    fun deleteByRfidTag(apiKey: String, rfidTag: String)
    fun edit(apiKey: String, rfidTag: String, editRequestBody: ItemEditRequestBody)
    fun getAllItemWithoutSection(apiKey: String): List<Item>
}
