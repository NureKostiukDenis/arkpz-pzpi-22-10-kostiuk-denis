package org.anware.presentation.controler

import org.anware.data.dto.product.ProductEditRequestBody
import org.anware.data.dto.ItemModel
import org.anware.data.repostory.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/product")
class ProductService(private val productRepository: ProductRepository) {

    @GetMapping("/list-warehouse")
    fun getAllProductsFromWarehouse(@RequestParam warehouseId: String): List<ItemModel> {
        TODO()
    }

    @GetMapping("/list-warehouse-section")
    fun getAllProductsFromSection(@RequestParam warehouseId: String, @RequestParam sectionId: String): List<ItemModel> {
        TODO()
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    fun addProduct(@RequestParam product: ItemModel): ItemModel {
        TODO()
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    fun deleteProduct(@RequestParam productId: String) {
        TODO()
    }

    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    fun editProduct(@RequestBody productEditRequestBody: ProductEditRequestBody) {
        TODO()
    }

}
