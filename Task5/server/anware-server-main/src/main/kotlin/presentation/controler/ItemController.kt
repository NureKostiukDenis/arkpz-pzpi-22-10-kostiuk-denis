package org.anware.presentation.controler

import org.anware.data.dto.item.AddItemRequest
import org.anware.data.dto.item.ItemEditRequestBody
import org.anware.data.dto.item.ItemListResponse
import org.anware.data.dto.item.toItemDetail
import org.anware.domain.usecase.ItemUseCase
import org.anware.domain.usecase.UserUseCases
import org.anware.presentation.Headers.Companion.API_KEY_HEADER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/item")
class ItemController @Autowired constructor(
    private val itemUseCase: ItemUseCase,
    private val userUseCases: UserUseCases
) {

    @GetMapping("/list-warehouse")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    fun getAllProductsFromWarehouse(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
    ): ItemListResponse {
        val items = itemUseCase.getAllInWarehouse(apiKey)
        val response =  ItemListResponse(
            status = "successful",
            totalProducts = items.size,
            items = items.map { it.toItemDetail() }
        )

        return response
    }

    @GetMapping("/list-warehouse-section")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    fun getGetAllItemsInWarehouseSection(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestParam sectionName: String,
    ): ItemListResponse {
        val items = itemUseCase.getAllInSection(apiKey, sectionName)
        val response =  ItemListResponse(
            status = "successful",
            totalProducts = items.size,
            items = items.map { it.toItemDetail() }
        )

        return response
    }

    @GetMapping("/list-without-section")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    fun getGetAllItemsInWarehouseWithoutSection(
        @RequestHeader(API_KEY_HEADER) apiKey: String
    ): ItemListResponse {
        val items = itemUseCase.getAllItemWithoutSection(apiKey).map { it.toItemDetail() }
        val response =  ItemListResponse(
            status = "successful",
            totalProducts = items.size,
            items = items
        )
        return response
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestBody item: AddItemRequest
    ) {
        itemUseCase.add(apiKey, item)
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    fun deleteProduct(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestParam rfidTag: String
    ) {
        itemUseCase.deleteByRfidTag(apiKey, rfidTag)
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    fun editProduct(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestParam rfidTag: String,
        @RequestBody itemEditRequestBody: ItemEditRequestBody
    ){
        itemUseCase.edit(apiKey, rfidTag, itemEditRequestBody)
    }
}
