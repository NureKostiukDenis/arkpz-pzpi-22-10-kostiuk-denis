package org.anware.data.dto.item

data class AddItemRequest(
    val rfidTag: String,
    val name: String,
)