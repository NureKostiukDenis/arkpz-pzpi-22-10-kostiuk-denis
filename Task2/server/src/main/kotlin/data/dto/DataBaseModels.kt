package org.anware.data.dto

import com.google.type.DateTime
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "items")
data class ItemModel(
    @Id
    val id: Long,
    @NotNull
    @Column(name = "rfid_tag")
    val rfidTag: String,
    val name: String,
    @Column(name = "created_at")
    val createdAt: DateTime,
    @Column(name = "updated_at")
    val updatedAt: DateTime
)

@Entity
@Table(name = "warehouses")
data class WarehouseModel(
    @Id
    val id: Int,
    val name: String
)

@Entity
@Table(name = "sections")
data class WarehouseSectionModel(
    @Id
    val id: Int,
    val name: String,
    @Column(name = "start_section_gate_id")
    val startSectionGateId: Int,
    @Column(name = "end_section_gate_id")
    val endSectionGateId: Int,
)

@Entity
@Table(name = "gates")
data class GateModel(
    @Id
    val id: Int,
)

@Entity
@Table(name = "item_locations")
data class ItemLocationModel(
    @Id
    @Column(name = "item_id")
    val itemId: Int,
    @Id
    @Column(name = "section_id")
    val sectionId: Int,
)

@Entity
@Table(name = "movements")
data class MovementModel(
    @Id
    @Column(name = "item_id")
    val itemId: Int,
    @Id
    @Column(name = "section_id")
    val sectionId: Int,
    @Column(name = "updated_at")
    val updatedAt: DateTime
)
