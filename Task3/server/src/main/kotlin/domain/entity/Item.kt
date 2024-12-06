package org.anware.domain.entity


data class Item(
    val id: Int,
    val rfidTag: String?,
    val name: String,
    val createdAt: java.time.LocalDateTime,
    val updatedAt: java.time.LocalDateTime
)