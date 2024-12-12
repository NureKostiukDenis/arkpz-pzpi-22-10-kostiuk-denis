package org.anware.domain.usecase

import org.anware.core.exeptions.UserHasWarehouseException
import org.anware.data.service.GateApiKeyService
import org.anware.data.service.UserService
import org.anware.data.service.WarehouseApiKeyService
import org.anware.data.service.WarehouseService
import org.anware.domain.handler.ApiKeyInvalidException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WarehouseUseCases @Autowired constructor(
    private val warehouseService: WarehouseService,
    private val userService: UserService,
    private val warehouseApiKeyService: WarehouseApiKeyService,
    private val gateApiKeyService: GateApiKeyService,
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

    fun getWarehouseAPIKey(userUID: String): String{
        val warehouse = userService.findWarehouseByUserUID(userUID)
            ?: throw UserHasWarehouseException("Warehouse for this user does`nt exist")

        val signature = WarehouseApiKeyService.WarehouseApiKeySignature(
            warehouseId = warehouse.id!!.toString(),
            warehouseName = warehouse.name!!,
            warehousePassword = warehouse.password,
            type = WarehouseApiKeyService.Type.READ
        )

        val apiKey = warehouseApiKeyService.generateApiKey(signature)
            ?: throw ApiKeyInvalidException("ApiKey generate impossible")

        return apiKey
    }

    fun getGateAPIKey(warehouseApiKey: String, userUID: String): String{
        val userWarehouse = userService.findWarehouseByUserUID(userUID)
            ?: throw UserHasWarehouseException("Warehouse for this user does`nt exist")

        val signature = GateApiKeyService.GateApiKeySignature(
            warehouseId = userWarehouse.id!!.toString(),
            warehouseName = userWarehouse.name!!,
            warehousePassword = userWarehouse.password,
            userUID = userUID
        )

        val apiKey = gateApiKeyService.generateApiKey(signature)
            ?: throw ApiKeyInvalidException("ApiKey generate impossible")

        return apiKey
    }

    fun addUserToWarehouse(userUID: String, apiKey: String){
        val warehouseSignature = warehouseApiKeyService.extractDataFromApiKey(apiKey)
            ?: throw ApiKeyInvalidException("ApiKey doesn`t exist")

        userService.attachUserToWarehouse(userUID, warehouseSignature.warehouseId.toInt())
    }

    fun deleteWarehouse(apiKey: String, userUID: String){
        val userWarehouse = userService.findWarehouseByUserUID(userUID)
        val warehouseSignature = warehouseApiKeyService.extractDataFromApiKey(apiKey)!!

        if (userWarehouse!!.id != warehouseSignature.warehouseId.toInt()){
            throw IllegalArgumentException()
        }

        warehouseService.deleteWarehouse(warehouseSignature.warehouseId.toInt())
    }

    fun detachUserFromWarehouse(userUID: String, apiKey: String){
        val warehouseSignature = warehouseApiKeyService.extractDataFromApiKey(apiKey)!!
        userService.detachUserFromWarehouse(userUID, warehouseSignature.warehouseId.toInt())
    }
}

