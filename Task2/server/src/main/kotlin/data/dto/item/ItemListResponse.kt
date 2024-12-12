package org.anware.data.dto.item
import org.anware.domain.entity.Item
import java.time.LocalDateTime

data class ItemListResponse(
    val status: String,
    val totalProducts: Int,
    val items: List<ItemDetail>
)

data class ItemDetail(
    val rfidTag: String,
    val name: String,
    val lastMovementDate: LocalDateTime
)

fun Item.toItemDetail(): ItemDetail{
    return ItemDetail(
        rfidTag = this.rfidTag!!,
        name = this.name!!,
        lastMovementDate = this.updatedAt
    )
}
