package org.anware.domain.usecase

import org.anware.data.dto.item.ItemEditRequestBody
import org.anware.domain.entity.Item
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class ItemUseCasesIml: ItemUseCase {

    override fun add(warehouseId: Int, item: Item) {
        TODO("Not yet implemented")
    }

    override fun getAllInWarehouse(warehouseId: Int): List<Item> {
        TODO("Not yet implemented")
    }

    override fun getAllInSection(warehouseId: Int, sectionName: String): List<Item> {
        TODO("Not yet implemented")
    }

    override fun getByRfidTag(warehouseId: Int, rfidTag: String): Item {
        TODO("Not yet implemented")
    }

    override fun deleteByRfidTag(warehouseId: Int, id: String) {
        TODO("Not yet implemented")
    }

    override fun edit(warehouseId: Int, editRequestBody: ItemEditRequestBody) {
        TODO("Not yet implemented")
    }
}