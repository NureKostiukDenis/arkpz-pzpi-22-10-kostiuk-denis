package org.anware.core.extensions

import org.anware.database.models.ProductModel
import org.anware.dto.ProductPayload

fun ProductPayload.toEntity(): ProductModel {
    return ProductModel(
        id = this.id
    )
}