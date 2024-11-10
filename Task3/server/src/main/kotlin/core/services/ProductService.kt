package org.anware.core.services

import org.anware.database.controler.ProductORM
import org.anware.database.models.ProductModel
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductORM) {

    fun addProduct(product: ProductModel): ProductModel = productRepository.save(product)

    fun getAllProducts(): List<ProductModel> = productRepository.findAll()

    fun deleteProduct(id: String) {
        productRepository.deleteById(id)
    }
}
