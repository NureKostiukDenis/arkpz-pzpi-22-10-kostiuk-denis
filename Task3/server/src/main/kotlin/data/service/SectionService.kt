package org.anware.data.service

import org.anware.data.dto.ItemModel
import org.anware.data.dto.WarehouseSectionModel
import org.anware.data.repository.SectionRepository

class SectionService(private val sectionRepository: SectionRepository) {
    fun findSectionById(id: Int): WarehouseSectionModel? {
        return sectionRepository.findById(id).orElse(null)
    }

    fun findSectionByName(name: String): WarehouseSectionModel? {
        return sectionRepository.findByName(name) // Предполагается, что есть метод findByName
    }

    fun findSectionsInWarehouse(warehouseId: Int): List<WarehouseSectionModel> {
        return sectionRepository.findAllByWarehouseId(warehouseId) // Метод в репозитории для поиска по складу
    }

    fun saveSection(section: WarehouseSectionModel): WarehouseSectionModel {
        return sectionRepository.save(section)
    }

    fun deleteSectionById(id: Int) {
        sectionRepository.deleteById(id)
    }
}
