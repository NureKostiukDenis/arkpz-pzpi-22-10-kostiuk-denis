package org.anware.presentation.controler

import org.anware.core.exeptions.UserHasWarehouseException
import org.anware.data.dto.CreateWarehouseRequest
import org.anware.domain.usecase.UserUseCases
import org.anware.domain.usecase.WarehouseUseCases
import org.anware.presentation.Headers.Companion.API_KEY_HEADER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/warehouse")
class WarehouseController @Autowired constructor(
    val warehouseUseCases: WarehouseUseCases,
    val userUseCases: UserUseCases
) {

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    private fun createWarehouse(@RequestBody body: CreateWarehouseRequest){
        val uid = SecurityContextHolder.getContext().authentication.principal as String
        warehouseUseCases.create(body.name, body.password, uid)
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    private fun deleteWarehouse(
        @RequestHeader(name = API_KEY_HEADER) apiKey: String
    ){
        val uid = SecurityContextHolder.getContext().authentication.principal as String
        warehouseUseCases.deleteWarehouse(apiKey, uid)
    }

    @GetMapping("/api-key")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    private fun getApiKey(): String{
        val uid = SecurityContextHolder.getContext().authentication.principal as String
        return warehouseUseCases.getWarehouseAPIKey(uid)
    }

    @GetMapping("/gate-api-key")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    private fun getGateApiKey(
        @RequestHeader(name = API_KEY_HEADER) apiKey: String
    ): String{
        val uid = SecurityContextHolder.getContext().authentication.principal as String
        return warehouseUseCases.getGateAPIKey(apiKey, uid)
    }

    @PostMapping("/log-in")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    private fun loginToWarehouse(
        @RequestHeader(name = API_KEY_HEADER) apiKey: String
    ){
        val uid = SecurityContextHolder.getContext().authentication.principal as String
        warehouseUseCases.addUserToWarehouse(uid, apiKey)
    }

    @PostMapping("/grant-admin")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    private fun makeUserAdmin(
        @RequestHeader(name = API_KEY_HEADER) apiKey: String,
        @RequestParam(name = "userUID") uid: String
    ){
        userUseCases.grantUserAdmin(uid, apiKey)
    }

    @PostMapping("/detach-user")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    private fun deleteUserFromWarehouse(
        @RequestHeader(name = "ApiKey") apiKey: String,
        @RequestParam(name = "userUID") uid: String
    ){
        warehouseUseCases.detachUserFromWarehouse(uid, apiKey)
    }

}
