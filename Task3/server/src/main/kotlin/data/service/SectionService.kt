package org.anware.data.service

import org.anware.data.dto.SectionModel
import org.anware.data.repository.ItemLocationRepository
import org.anware.data.repository.SectionRepository
import org.anware.domain.entity.Section
import org.anware.domain.entity.toSection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SectionService @Autowired constructor(
    private val sectionRepository: SectionRepository,
    private val itemLocationRepository: ItemLocationRepository,
    private val warehouseService: WarehouseService
) {
    fun findById(id: Int): Section? {
        return sectionRepository.findById(id).orElse(null)?.toSection()
    }

    fun findByName(warehouseId: Int, name: String): Section? {
        return sectionRepository.findByNameInWarehouse(name, warehouseId)?.toSection()
    }

    fun findInWarehouse(warehouseId: Int): List<Section>? {
        return sectionRepository.findAllByWarehouseId(warehouseId)?.map { it.toSection() }
    }

    fun save(section: Section): Section {
        val warehouse = warehouseService.getById(section.warehouseId)

        val sectionModel = SectionModel(
            id = section.id,
            name = section.name,
            capacity = section.capacity,
            warehouse = warehouse
        )

        return sectionRepository.save(sectionModel).toSection()
    }

    fun delete(id: Int) {
        sectionRepository.deleteById(id)
    }

    @Transactional
    fun delete(warehouseId: Int, name: String) {
        val section = sectionRepository.findByNameInWarehouse(name, warehouseId)
            ?: throw IllegalArgumentException("Section with name: $name does`nt exist")
        sectionRepository.deleteById(section.id!!)
    }

}
