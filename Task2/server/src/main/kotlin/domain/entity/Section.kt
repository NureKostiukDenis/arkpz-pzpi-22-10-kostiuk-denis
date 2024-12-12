package org.anware.domain.entity

import org.anware.data.dto.SectionModel

data class Section(
    val id: Int?,
    var name: String,
    var capacity: Int,
    val warehouseId: Int
)

fun SectionModel.toSection(): Section {
    return Section(
        id = this.id!!,
        name = this.name!!,
        capacity = this.capacity,
        warehouseId = this.warehouse.id!!
    )
}
