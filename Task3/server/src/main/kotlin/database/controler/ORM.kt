package org.anware.database.controler

import org.anware.database.models.ProductModel
import org.anware.database.models.Warehouse
import org.anware.database.models.WarehouseSection
import org.springframework.data.jpa.repository.JpaRepository

interface ProductORM : JpaRepository<ProductModel, String>

interface WarehouseORM : JpaRepository<Warehouse, String>

interface SectionORM : JpaRepository<WarehouseSection, String>
