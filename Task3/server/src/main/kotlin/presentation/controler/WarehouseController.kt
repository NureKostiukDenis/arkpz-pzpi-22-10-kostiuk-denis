package org.anware.presentation.controler

import org.anware.data.dto.CreateWarehouseRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
class WarehouseController {

    @GetMapping("/create-warehouse")
    @ResponseStatus(HttpStatus.OK)
    private fun createWarehouse(@RequestBody body: CreateWarehouseRequest){

    }

    @GetMapping("/get-api-key/{warehouseId}")
    @ResponseStatus(HttpStatus.OK)
    private fun getApiKey(@PathVariable warehouseId: Int): String{
        return "IDI NAHUI"
    }

}