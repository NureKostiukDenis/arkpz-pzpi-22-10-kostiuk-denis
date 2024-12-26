package org.anware.domain.usecase

import org.anware.data.service.UserService
import org.anware.data.service.WarehouseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UserUseCases @Autowired constructor(
    private val userService: UserService,
    private val warehouseService: WarehouseService
)
{

    fun grantUserAdmin(userUID: String, apiKey: String){
        val warehouse = warehouseService.getByAPIKey(apiKey)!!
        val userWarehouse = userService.findWarehouseByUserUID(userUID)
            ?: throw IllegalArgumentException()

        if (warehouse.id != userWarehouse.id){
            throw IllegalArgumentException()
        }

        userService.makeUserAdmin(userUID)
    }

    fun userIsInWarehouse(apiKey: String, uid: String): Boolean{
        val userWarehouse = userService.findWarehouseByUserUID(uid)!!
        val apiKeyWarehouse = warehouseService.getByAPIKey(apiKey)!!

        return userWarehouse.id!! == apiKeyWarehouse.id!!
    }

}