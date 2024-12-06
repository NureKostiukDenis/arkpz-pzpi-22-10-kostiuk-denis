package org.anware.domain.usecase

import org.anware.domain.entity.Section

class SectionsUseCasesImpl: SectionsUseCases {
    override fun add(warehouseId: Int, section: Section) {
        TODO("Not yet implemented")
    }

    override fun getAllInWarehouse(warehouseId: Int): List<Section> {
        TODO("Not yet implemented")
    }

    override fun findByName(warehouseId: Int, name: String): Section {
        TODO("Not yet implemented")
    }

    override fun editSection(warehouseId: Int, name: String, newData: Section) {
        TODO("Not yet implemented")
    }
}