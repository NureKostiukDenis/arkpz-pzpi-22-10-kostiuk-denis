package org.anware.data.repository

import org.anware.data.dto.ItemLocationId
import org.anware.data.dto.ItemLocationModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemLocationRepository : JpaRepository<ItemLocationModel, ItemLocationId> {

    @Modifying
    @Query(
        """
            UPDATE ItemLocationModel il
            SET il.section.id = :newSectionId
            WHERE il.item.id = :itemId AND il.section.id = :oldSectionId
        """
    )
    fun updateLocation(
        @Param("itemId") itemId: Int,
        @Param("oldSectionId") oldSectionId: Int,
        @Param("newSectionId") newSectionId: Int
    ): Int
}