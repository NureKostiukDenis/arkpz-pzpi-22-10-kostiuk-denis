package org.anware.data.dto

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user")
data class UserModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "uid", unique = true, nullable = true)
    val uid: String? = null,

    @Column(name = "role", nullable = true)
    val role: String? = null
)

@Entity
@Table(name = "warehouse")
data class WarehouseModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "name", nullable = true)
    val name: String? = null,

    @Column(name = "password", nullable = true)
    val password: String? = null
)

@Entity
@Table(name = "user_warehouse")
data class UserWarehouseModel(
    @EmbeddedId
    val id: UserWarehouseId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("warehouseId")
    @JoinColumn(name = "warehouse_id", nullable = false)
    val warehouse: WarehouseModel
)

@Embeddable
data class UserWarehouseId(
    @Column(name = "user_id")
    val userId: Int,

    @Column(name = "warehouse_id")
    val warehouseId: Int
)

@Entity
@Table(name = "gate")
data class GateModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null
)

@Entity
@Table(name = "section")
data class SectionModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    val warehouse: WarehouseModel,

    @Column(name = "name", nullable = true)
    val name: String? = null,

    @Column(name = "capacity", nullable = false)
    val capacity: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_gate_id", nullable = false)
    val startGate: GateModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_gate_id", nullable = false)
    val endGate: GateModel
)

@Entity
@Table(name = "item")
data class ItemModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "rfid_tag", nullable = true)
    val rfidTag: String? = null,

    @Column(name = "name", nullable = true)
    val name: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime
)

@Entity
@Table(name = "item_location")
data class ItemLocationModel(
    @EmbeddedId
    val id: ItemLocationId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")
    @JoinColumn(name = "item_id", nullable = false)
    val item: ItemModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sectionId")
    @JoinColumn(name = "section_id", nullable = false)
    val section: SectionModel
)

@Embeddable
data class ItemLocationId(
    @Column(name = "item_id")
    val itemId: Int,

    @Column(name = "section_id")
    val sectionId: Int
)

@Entity
@Table(name = "movment")
data class MovementModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    val item: ItemModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    val section: SectionModel,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime
)
