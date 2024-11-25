package org.anware.data.controler

import org.anware.core.repostories.ProductRepository
import org.anware.database.models.ProductModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductService(private val productRepository: ProductRepository) {

    @GetMapping
    fun getAllProducts(): List<ProductModel> {
        return productRepository.getAllProducts()
    }

    @PostMapping
    fun addProduct(@RequestBody product: ProductModel): ProductModel {
        return productRepository.addProduct(product)
    }
}
