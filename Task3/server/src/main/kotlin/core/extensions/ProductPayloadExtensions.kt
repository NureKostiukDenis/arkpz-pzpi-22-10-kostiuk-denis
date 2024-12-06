package org.anware.core.extensions

import org.anware.database.models.ProductModel
import org.anware.dto.ProductMovementPayload

fun ProductMovementPayload.toEntity(): ProductModel {
    return ProductModel(
        id = this.id,
        name = this.name,
        sectionId = this.sectionId
    )
}