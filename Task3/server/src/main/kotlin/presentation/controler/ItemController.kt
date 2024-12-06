package org.anware.presentation.controler

import org.anware.data.dto.item.ItemEditRequestBody
import org.anware.domain.entity.Item
import org.anware.domain.usecase.ItemUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/{warehouseId}/item")
class ItemController @Autowired constructor(
     private val itemUseCase: ItemUseCase
) {

    @GetMapping("/list-warehouse")
    fun getAllProductsFromWarehouse(
        @PathVariable warehouseId: Int
    ): List<Item> {
        return itemUseCase.getAllInWarehouse(warehouseId)
    }

    @GetMapping("/list-warehouse-section")
    fun getAllProductsFromSection(
        @PathVariable warehouseId: Int,
        @RequestParam sectionName: String
    ): List<Item> {
        return itemUseCase.getAllInSection(warehouseId, sectionName)
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(
        @PathVariable warehouseId: Int,
        @RequestBody product: Item
    ) {
        itemUseCase.add(warehouseId, product)
    }

    @DeleteMapping("/delete/{productRfidTag}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteProduct(
        @PathVariable warehouseId: Int,
        @PathVariable productRfidTag: String
    ) {
        itemUseCase.deleteByRfidTag(warehouseId, productRfidTag)
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    fun editProduct(
        @PathVariable warehouseId: Int,
        @RequestBody itemEditRequestBody: ItemEditRequestBody
    ): Item {
        itemUseCase.edit(warehouseId, itemEditRequestBody)
        return itemUseCase.getByRfidTag(warehouseId, itemEditRequestBody.rfidTag)
    }
}
