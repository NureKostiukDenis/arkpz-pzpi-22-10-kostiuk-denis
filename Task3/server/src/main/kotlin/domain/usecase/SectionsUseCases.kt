package org.anware.domain.usecase

import org.anware.data.dto.item.ItemEditRequestBody
import org.anware.domain.entity.Item
import org.anware.domain.entity.Section

interface SectionsUseCases {
    fun add(warehouseId: Int, section: Section)
    fun getAllInWarehouse(warehouseId: Int): List<Section>
    fun findByName(warehouseId: Int, name: String) : Section
    fun editSection(warehouseId: Int, name: String, newData: Section)
}