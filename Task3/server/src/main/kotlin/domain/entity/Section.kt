package org.anware.domain.entity

import jakarta.persistence.*
import org.anware.data.dto.GateModel
import org.anware.data.dto.WarehouseModel

data class Section(
    val id: Int,
    val name: String,
    val startGate: GateModel,
    val endGate: GateModel,
    val warehouse: WarehouseModel,
    val capacity: Int
)
