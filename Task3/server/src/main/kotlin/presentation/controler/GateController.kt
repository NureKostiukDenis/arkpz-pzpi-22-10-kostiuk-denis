package org.anware.presentation.controler

import org.anware.data.dto.gate.AddGateRequest
import org.anware.data.dto.gate.AttachGateToSectionRequest
import org.anware.data.dto.gate.DetachGateFromSectionRequest
import org.anware.data.dto.gate.EditGateRequest
import org.anware.domain.usecase.GateUseCases
import org.anware.domain.usecase.UserUseCases
import org.anware.presentation.Headers.Companion.API_KEY_HEADER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/gate")
class GateController @Autowired constructor(
    private val gateUseCases: GateUseCases
) {

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    fun addNewSection(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestBody gate: AddGateRequest
    ) {
        gateUseCases.add(apiKey, gate)
    }

    @PostMapping("/attach")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    fun attachToSection(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestBody body: AttachGateToSectionRequest
    ) {
        gateUseCases.attachToSection(apiKey, body)
    }

    @PostMapping("/detach")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    fun detachToSection(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestBody body: DetachGateFromSectionRequest
    ) {
        gateUseCases.detachFromSection(apiKey, body)
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    fun deleteSection(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestParam code: String
    ) {
        gateUseCases.delete(apiKey, code)
    }

}