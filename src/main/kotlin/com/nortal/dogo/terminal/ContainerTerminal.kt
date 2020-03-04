package com.nortal.dogo.terminal

import java.time.Clock
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

class ContainerTerminal(private val clock: Clock = Clock.systemDefaultZone()) {

    companion object {
        private const val PRICE_PER_DAY = 100
    }

    private val slots = Array(30) { Array<ContainerSlot?>(30) { null } }

    fun getFreeSlots(): Int {
        return 900 - slots.sumBy { it.count { containerSlot -> containerSlot != null } };
    }

    fun storeContainer(container: Container) {
        slots[0][0] = ContainerSlot(container = container, addedTime = LocalDateTime.now(clock))
    }

    fun removeContainer(id: UUID): Bill {
        var removedSlot: ContainerSlot? = null
        slots.forEachIndexed { row, arrayOfContainerSlots ->
            arrayOfContainerSlots.forEachIndexed { index, containerSlot ->
                if (containerSlot?.container?.id == id) {
                    removedSlot = containerSlot
                    slots[row][index] = null
                }
            }
        }

        return removedSlot?.let { calculateBill(it) } ?: throw IllegalArgumentException(id.toString())
    }

    private fun calculateBill(containerSlot: ContainerSlot): Bill {
        var days = Duration.between(LocalDateTime.now(clock), containerSlot.addedTime).toDays()

        if (days == 0L) {
            days = 1L
        }

        return Bill(containerId = containerSlot.container.id, cost = days * PRICE_PER_DAY)
    }

}
