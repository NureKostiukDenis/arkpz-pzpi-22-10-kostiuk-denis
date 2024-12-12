package org.anware.data.dto.gate

data class AttachGateToSectionRequest(
    val sectionName: String,
    val gateCode: String,
    val connectionType: String
)

data class DetachGateFromSectionRequest(
    val sectionName: String,
    val gateCode: String
)