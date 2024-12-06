package org.anware.database.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "product")
data class ProductModel(
    @Id
    val id: Long,
    val name: String,
    val sectionId: Long
)

@Entity
@Table(name = "warehouse")
data class Warehouse(
    @Id
    val id: Int,
)

@Entity
@Table(name = "section")
data class WarehouseSection(
    @Id
    val id: Int
)
