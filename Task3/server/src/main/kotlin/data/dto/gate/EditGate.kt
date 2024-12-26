package org.anware.data.dto.gate

data class EditGateRequest(
    val code: String,
    val sectionName: String,
    val type: String
)