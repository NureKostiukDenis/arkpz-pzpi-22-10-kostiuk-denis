package org.anware.data.dto

import com.google.type.DateTime
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "employees")
data class EmployeeModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "firebase_uid", unique = true, nullable = false)
    val firebaseUid: String,

    @Column(name = "is_admin", nullable = false)
    val isAdmin: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouses_id", nullable = false)
    val warehouse: WarehouseModel
)


@Entity
@Table(name = "items")
data class ItemModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "rfid_tag", unique = true, nullable = true)
    val rfidTag: String?,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: java.time.LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: java.time.LocalDateTime
)


@Entity
@Table(name = "warehouses")
data class WarehouseModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "name", nullable = false)
    val name: String
)


@Entity
@Table(name = "sections")
data class WarehouseSectionModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "name", unique = true, nullable = false)
    val name: String,

    @OneToOne
    @JoinColumn(name = "start_section_gate_id", referencedColumnName = "id", nullable = false)
    val startGate: GateModel,

    @OneToOne
    @JoinColumn(name = "end_section_gate_id", referencedColumnName = "id", nullable = false)
    val endGate: GateModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouses_id", nullable = false)
    val warehouse: WarehouseModel,

    @Column(name = "capacity", nullable = false)
    val capacity: Int
)

@Entity
@Table(name = "gates")
data class GateModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int
)


@Entity
@Table(name = "movements")
data class MovementModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    val itemLocation: ItemLocationsModel,

    @Column(name = "updated_at")
    val updatedAt: java.time.LocalDateTime?
)



@Entity
@Table(name = "item_locations")
data class ItemLocationsModel(
    @EmbeddedId
    val id: ItemLocationId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")  // связываем itemId из составного идентификатора
    @JoinColumn(name = "item_id", nullable = false)
    val item: ItemModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sectionId")  // связываем sectionId из составного идентификатора
    @JoinColumn(name = "section_id", nullable = false)
    val section: WarehouseSectionModel
)


@Embeddable
data class ItemLocationId(
    @Column(name = "item_id")
    val itemId: Int,

    @Column(name = "section_id")
    val sectionId: Int
)
