package org.anware.data.service

import org.anware.data.dto.SectionModel
import org.anware.data.repository.SectionRepository

class SectionService(private val sectionRepository: SectionRepository) {
    fun findSectionById(id: Int): SectionModel? {
        return sectionRepository.findById(id).orElse(null)
    }

    fun findSectionByName(name: String): SectionModel? {
        return sectionRepository.findByName(name)
    }

    fun findSectionsInWarehouse(warehouseId: Int): List<SectionModel> {
        return sectionRepository.findAllByWarehouseId(warehouseId)
    }

    fun saveSection(section: SectionModel): SectionModel {
        return sectionRepository.save(section)
    }

    fun deleteSectionById(id: Int) {
        sectionRepository.deleteById(id)
    }
}
