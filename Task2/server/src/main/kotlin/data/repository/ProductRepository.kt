package org.anware.data.repostory

import org.anware.data.dto.ItemModel
import org.springframework.stereotype.Service

@Service
class ProductRepository(private val itemRepository: ItemRepository) {

    fun addProduct(product: ItemModel): ItemModel = itemRepository.save(product)

    fun getAllProducts(): List<ItemModel> = itemRepository.findAll()

    fun deleteProduct(id: String) {
        itemRepository.deleteById(id)
    }

    fun editProduct(){

    }
}
