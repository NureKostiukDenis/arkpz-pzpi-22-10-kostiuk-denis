package org.anware.data.service

import org.anware.data.dto.MovementModel
import org.anware.data.repository.ItemLocationRepository
import org.anware.data.repository.ItemRepository
import org.anware.data.repository.MovementRepository
import org.anware.data.repository.SectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MovementService
@Autowired constructor(
    private val sectionRepository: SectionRepository,
    private val movementRepository: MovementRepository,
    private val itemLocationRepository: ItemLocationRepository,
    private val itemRepository: ItemRepository
): Logger()
{

    @Transactional
    fun moveItem(warehouseId: Int, itemRfidTag: String, newSectionName: String): Boolean{
        val item = itemRepository.findByRfidTag(itemRfidTag, warehouseId)
            ?: throw Exception("")

        logger.info(item.id)
        val newSection = sectionRepository.findByNameInWarehouse(newSectionName, warehouseId)
            ?: throw Exception("")

        logger.info(newSection.name)
        val oldSection = sectionRepository.findByItemRfidTagInWarehouse(warehouseId, itemRfidTag)
            ?: throw Exception("")

        logger.info(oldSection.name)
        val updatedItemsCount = itemLocationRepository.updateLocation(item.id!!, oldSection.id!!, newSection.id!!)

        if (updatedItemsCount <= 0){
            return false
        }

        movementRepository.save(MovementModel(
            item = item,
            newSection = newSection,
            oldSection = oldSection,
            updatedAt = LocalDateTime.now()
        ))

        val res = itemLocationRepository.updateLocation(item.id, oldSection.id, newSection.id)

        return res == 1
    }

}