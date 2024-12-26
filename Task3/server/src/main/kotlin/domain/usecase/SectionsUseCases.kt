package org.anware.domain.usecase

import org.anware.data.dto.item.ItemEditRequestBody
import org.anware.data.dto.section.AddSectionRequest
import org.anware.data.dto.section.DeleteSectionRequest
import org.anware.data.dto.section.EditSectionRequest
import org.anware.domain.entity.Item
import org.anware.domain.entity.Section

interface SectionsUseCases {
    fun add(section: Section)
    fun add(section: AddSectionRequest, apiKey: String)
    fun getAllInWarehouse(warehouseId: Int): List<Section>
    fun findByName(warehouseId: Int, name: String) : Section?
    fun editSection(warehouseId: Int, name: String, newData: Section)
    fun editSection(apiKey: String, name: String, newData: EditSectionRequest)
    fun deleteSection(apiKey: String, body: String)
}