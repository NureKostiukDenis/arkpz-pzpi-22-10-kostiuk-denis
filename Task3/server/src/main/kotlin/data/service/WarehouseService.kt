package org.anware.data.service

import org.anware.data.dto.WarehouseModel
import org.anware.data.repository.WarehouseRepository
import org.anware.domain.handler.ApiKeyInvalidException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WarehouseService @Autowired constructor(
    private val warehouseRepository: WarehouseRepository,
    private val apiKeyService: ApiKey
) {

    fun getWarehouseByAPIKey(apiKey: String): WarehouseModel{
        val warehouseId = apiKeyService.extractDataFromApiKey(apiKey)?.warehouseId ?: throw ApiKeyInvalidException("Api key is null")
        val warehouse = warehouseRepository.getReferenceById(warehouseId.toInt())
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



}