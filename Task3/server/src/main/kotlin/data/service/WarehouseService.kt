package org.anware.data.service

import org.anware.data.dto.WarehouseModel
import org.anware.data.repository.WarehouseRepository
import org.anware.domain.handler.ApiKeyInvalidException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WarehouseService @Autowired constructor(
    private val warehouseRepository: WarehouseRepository,
    private val apiKeyService: WarehouseApiKeyService
) {

    fun getByAPIKey(apiKey: String): WarehouseModel?{
        val warehouseId = apiKeyService.extractDataFromApiKey(apiKey) ?: return null
        return getById(warehouseId.warehouseId.toInt())
    }

    fun getById(id: Int): WarehouseModel{
        val warehouse = warehouseRepository.getReferenceById(id)
        return warehouse
    }

    fun createWarehouse(name: String, password: String): WarehouseModel{
        val warehouse = WarehouseModel(
            id = null,
            name = name,
            password = password
        )
        warehouseRepository.save(warehouse)
        return warehouse
    }

    fun deleteWarehouse(id: Int){
        warehouseRepository.deleteById(id)
    }

}