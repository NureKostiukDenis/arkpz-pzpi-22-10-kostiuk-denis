package org.anware.domain.entity

import org.anware.data.dto.ItemModel


data class Item(
    val id: Int?,
    val rfidTag: String,
    val name: String?,
    val warehouseId: Int,
    val createdAt: java.time.LocalDateTime,
    val updatedAt: java.time.LocalDateTime
)

fun ItemModel.toItem(): Item{
    val item = Item(
        id = this.id,
        rfidTag = this.rfidTag,
        name = this.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        warehouseId = this.warehouse.id!!
    )

    return item
}