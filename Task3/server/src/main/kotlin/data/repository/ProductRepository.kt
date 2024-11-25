package org.anware.core.repostories

import org.anware.database.controler.ProductORM
import org.anware.database.models.ProductModel
import org.springframework.stereotype.Service

@Service
class ProductRepository(private val productORM: ProductORM) {

    fun addProduct(product: ProductModel): ProductModel = productORM.save(product)

    fun getAllProducts(): List<ProductModel> = productORM.findAll()

    fun deleteProduct(id: String) {
        productORM.deleteById(id)
    }
}
