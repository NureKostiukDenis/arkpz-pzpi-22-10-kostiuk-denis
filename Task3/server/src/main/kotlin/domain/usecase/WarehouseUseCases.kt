package org.anware.domain.usecase

import org.anware.data.service.ApiKey
import org.anware.data.service.ApiKeyGenerator
import org.anware.data.service.UserService
import org.anware.data.service.WarehouseService
import org.anware.domain.handler.ApiKeyInvalidException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WarehouseUseCases @Autowired constructor(
    private val warehouseService: WarehouseService,
    private val userService: UserService,
    private val apiKeyGenerator: ApiKey
) {

    fun create(name: String, password: String, userUID: String){
        val warehouse = userService.findWarehouseByUserUID(userUID)
        if (warehouse == null){
            val newWarehouse = warehouseService.createWarehouse(name, password)
            userService.attachUserToWarehouse(userUID, newWarehouse.id!!)
            userService.makeUserAdmin(userUID)
        }else{
            throw UserHasWarehouseException("Warehouse for this user exist")
        }
    }

    fun getAPIKey(userUID: String): String{
        val warehouse = userService.findWarehouseByUserUID(userUID)
            ?: throw UserHasWarehouseException("Warehouse for this user doesn`t exist")

        val signature = ApiKeyGenerator.ApiKeySignature(
            warehouseId = warehouse.id!!.toString(),
            warehouseName = warehouse.name!!,
            warehousePassword = warehouse.password,
            type = ApiKeyGenerator.Type.READ
        )

        val apiKey = apiKeyGenerator.generateApiKey(signature)
            ?: throw ApiKeyInvalidException("ApiKey generate impossible")

        return apiKey
    }

    fun addUserToWarehouse(userUID: String, apiKey: String){
        val warehouseSignature = apiKeyGenerator.extractDataFromApiKey(apiKey)
            ?: throw ApiKeyInvalidException("ApiKey doesn`t exist")

        userService.attachUserToWarehouse(userUID, warehouseSignature.warehouseId.toInt())
    }
}

class UserHasWarehouseException(msg: String?): Exception(msg)