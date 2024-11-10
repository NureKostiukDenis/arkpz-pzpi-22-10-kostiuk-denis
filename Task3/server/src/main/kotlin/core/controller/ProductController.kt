package org.anware.core.controller

import org.anware.core.services.ProductService
import org.anware.database.models.ProductModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts(): List<ProductModel> {
        return productService.getAllProducts()
    }

    @PostMapping
    fun addProduct(@RequestBody product: ProductModel): ProductModel {
        return productService.addProduct(product)
    }
}
