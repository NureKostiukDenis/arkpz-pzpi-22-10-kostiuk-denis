package org.anware.data.service

import org.anware.data.dto.ItemModel
import org.anware.data.repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemService(private val itemRepository: ItemRepository) {

    fun findItemById(id: Int): ItemModel? {
        return itemRepository.findById(id).orElse(null)
    }

    fun findAllItems(): List<ItemModel> {
        return itemRepository.findAll()
    }

    fun saveItem(item: ItemModel): ItemModel {
        return itemRepository.save(item)
    }

    fun deleteItemById(id: Int){
        itemRepository.deleteById(id)
    }
}