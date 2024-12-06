package org.anware.presentation.controler

import org.anware.data.dto.CreateWarehouseRequest
import org.anware.domain.usecase.WarehouseUseCases
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/warehouse")
class WarehouseController @Autowired constructor(
    val warehouseUseCases: WarehouseUseCases
) {

    @PostMapping("/create-warehouse")
    @ResponseStatus(HttpStatus.OK)
    private fun createWarehouse(@RequestBody body: CreateWarehouseRequest){
        val uid = SecurityContextHolder.getContext().authentication.principal as String
        warehouseUseCases.create(body.name, body.password, uid)
    }

    @PostMapping("/get-api-key")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    private fun getApiKey(): String{
        val uid = SecurityContextHolder.getContext().authentication.principal as String
        return warehouseUseCases.getAPIKey(uid)
    }

    @PostMapping("/log-in")
    @ResponseStatus(HttpStatus.OK)
    private fun loginToWarehouse(
        @RequestHeader(name = "ApiKey") apiKey: String
    ){
        val uid = SecurityContextHolder.getContext().authentication.principal as String
        warehouseUseCases.addUserToWarehouse(uid, apiKey)
    }

}
