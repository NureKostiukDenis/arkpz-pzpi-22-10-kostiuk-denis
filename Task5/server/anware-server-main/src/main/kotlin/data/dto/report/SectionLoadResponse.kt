package org.anware.data.dto.report

data class SectionLoadResponse(
    val status: String,
    val warehouseName: String,
    val total: Int,
    val sections: List<SectionInfo>
)

data class SectionInfo(
    val name: String,
    val currentLoad: Int,
    val capacity: Int
)
