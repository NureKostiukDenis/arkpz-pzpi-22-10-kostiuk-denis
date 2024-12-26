package org.anware.domain.usecase

import org.anware.data.dto.gate.AddGateRequest
import org.anware.data.dto.gate.AttachGateToSectionRequest
import org.anware.data.dto.gate.DetachGateFromSectionRequest
import org.anware.data.dto.gate.EditGateRequest

interface GateUseCases {
    fun add(apiKey: String, gate: AddGateRequest)
    fun delete(apiKey: String, gateCode: String)
    fun edit(apiKey: String, code: String, newGateData: EditGateRequest)
    fun attachToSection(apiKey: String, attachData: AttachGateToSectionRequest)
    fun detachFromSection(apiKey: String, detachData: DetachGateFromSectionRequest)
}