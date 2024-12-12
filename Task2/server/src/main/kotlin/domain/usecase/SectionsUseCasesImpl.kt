package org.anware.domain.usecase

import org.anware.core.exeptions.SectionException
import org.anware.data.dto.section.AddSectionRequest
import org.anware.data.dto.section.EditSectionRequest
import org.anware.data.service.SectionService
import org.anware.data.service.WarehouseApiKeyService
import org.anware.domain.entity.Section
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SectionsUseCasesImpl @Autowired constructor(
    private val sectionService: SectionService,
    private val apiKeyServiceClazz: WarehouseApiKeyService
): SectionsUseCases {

    override fun add(section: Section) {
        val existSection = sectionService.findByName(section.warehouseId, section.name)
        if(existSection != null){
            throw SectionException("Section with name ${existSection.name} exists")
        }

        sectionService.save(section)
    }

    override fun add(section: AddSectionRequest, apiKey: String) {
        val warehouse = apiKeyServiceClazz.extractDataFromApiKey(apiKey)

        val newSection = Section(
            id = null,
            name = section.name,
            warehouseId = warehouse!!.warehouseId.toInt(),
            capacity = section.capacity
        )

        add(newSection)
    }

    override fun getAllInWarehouse(warehouseId: Int): List<Section> {
        val list = sectionService.findInWarehouse(warehouseId)
        return list ?: emptyList()
    }

    override fun findByName(warehouseId: Int, name: String): Section? {
        return sectionService.findByName(warehouseId, name)
    }

    override fun editSection(warehouseId: Int, name: String, newData: Section) {

        val existingSection = sectionService.findByName(warehouseId, name)
            ?: throw IllegalArgumentException("Section with name '$name' in warehouse not found.")

        existingSection.name = newData.name
        existingSection.capacity = newData.capacity

        sectionService.save(existingSection)
    }

    override fun editSection(apiKey: String, name: String, newData: EditSectionRequest) {
        val warehouse = apiKeyServiceClazz.extractDataFromApiKey(apiKey)
        val newSection = Section(
            id = null,
            name = newData.name,
            warehouseId = warehouse!!.warehouseId.toInt(),
            capacity = newData.capacity
        )
        editSection(warehouse.warehouseId.toInt(), name, newSection)
    }

    override fun deleteSection(apiKey: String, body: String) {
        val warehouse = apiKeyServiceClazz.extractDataFromApiKey(apiKey)
        sectionService.delete(warehouse!!.warehouseId.toInt(), body)
    }
}

