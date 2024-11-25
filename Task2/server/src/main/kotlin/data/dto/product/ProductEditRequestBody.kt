package org.anware.data.dto.product

data class ProductEditRequestBody(
    val id: String,
    val rfidTag: String,
    val name: String,
)