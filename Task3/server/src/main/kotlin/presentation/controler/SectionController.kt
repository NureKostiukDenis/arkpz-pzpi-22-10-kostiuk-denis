package org.anware.presentation.controler

import org.anware.data.dto.section.AddSectionRequest
import org.anware.data.dto.section.DeleteSectionRequest
import org.anware.data.dto.section.EditSectionRequest
import org.anware.domain.usecase.SectionsUseCases
import org.anware.domain.usecase.UserUseCases
import org.anware.presentation.Headers.Companion.API_KEY_HEADER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/section")
class SectionController @Autowired constructor(
    private val userUseCases: UserUseCases
) {

    @Autowired
    private lateinit var sectionsUseCases: SectionsUseCases

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    fun addNewSection(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestBody section: AddSectionRequest
    ) {
        sectionsUseCases.add(section, apiKey)
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun editSection(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestBody section: EditSectionRequest,
        @RequestParam sectionName: String
    ) {
        sectionsUseCases.editSection(apiKey, sectionName, section)

    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    fun deleteSection(
        @RequestHeader(API_KEY_HEADER) apiKey: String,
        @RequestParam sectionName: String
    ) {
        sectionsUseCases.deleteSection(apiKey, sectionName)
    }
}
