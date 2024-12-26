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

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole,
    )

enum class UserRole{
    USER, STAFF, ADMIN;

    companion object {
        fun getType(type: String): UserRole? {
            return when (type.lowercase()) {
                "user" -> USER
                "staff" -> STAFF
                "admin" -> ADMIN
                else -> null
            }
        }
    }
}

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
@Table(name = "section_gate")
data class SectionGateModel(
    @EmbeddedId
    val id: SectionGateId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("gateId")
    @JoinColumn(name = "gate_id", nullable = false)
    val gate: GateModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sectionId")
    @JoinColumn(name = "section_id", nullable = false)
    val section: SectionModel,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: GateType
)

@Embeddable
data class SectionGateId(
    @Column(name = "gate_id")
    val gateId: Int,

    @Column(name = "section_id")
    val sectionId: Int
)

@Entity
@Table(name = "gate")
data class GateModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "code", nullable = false)
    val code: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    val warehouse: WarehouseModel
)

enum class GateType {
    ENTRY, EXIT, START_EXIT;

    companion object {
        fun getType(type: String): GateType? {
            return when (type.lowercase()) {
                "entry" -> ENTRY
                "exit" -> EXIT
                "start_exit" -> START_EXIT
                else -> null
            }
        }
    }
}

@Entity
@Table(name = "section")
data class SectionModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    val warehouse: WarehouseModel,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "capacity", nullable = false)
    val capacity: Int
)

@Entity
@Table(name = "item")
data class ItemModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "rfid_tag", nullable = false)
    val rfidTag: String,

    @Column(name = "name", nullable = true)
    val name: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    val warehouse: WarehouseModel
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
@Table(name = "movement")
data class MovementModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    val item: ItemModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_section_id", referencedColumnName = "id", nullable = false)
    val newSection: SectionModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_section_id", referencedColumnName = "id", nullable = false)
    val oldSection: SectionModel,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime
)