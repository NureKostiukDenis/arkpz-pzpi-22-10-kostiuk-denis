package org.anware.data.repostory

import org.anware.data.dto.*
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<ItemModel, String>

interface WarehouseRepository : JpaRepository<WarehouseModel, String>

interface SectionRepository : JpaRepository<WarehouseSectionModel, String>

interface GateRepository : JpaRepository<GateModel, String>

interface MovementsRepository : JpaRepository<MovementModel, String>

interface ItemLocationRepository : JpaRepository<ItemLocationModel, String>
