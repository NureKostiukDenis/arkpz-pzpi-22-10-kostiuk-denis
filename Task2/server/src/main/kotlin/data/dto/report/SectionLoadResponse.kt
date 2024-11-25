package org.anware.data.dto.report

data class SectionLoadResponse(
    val status: String,
    val warehouseId: Long,
    val sectionId: Long,
    val sections: List<SectionInfo>
)

data class SectionInfo(
    val sectionId: Long,
    val name: String,
    val currentLoad: Int,
    val capacity: Int
)
