package com.nortal.dogo.terminal

import java.time.Clock
import java.util.*

class ContainerTerminal(clock: Clock = Clock.systemDefaultZone()) {

    private val slots: Array<Array<Container?>> = Array(30) { Array<Container?>(30) { null } }

    fun getFreeSpots(): Int {
        return 900 - slots.sumBy { it.count { container -> container != null } };
    }

    fun storeContainer(container: Container) {
        slots[0][0] = container
    }

    fun removeContainer(id: UUID): Bill {
        slots.forEachIndexed { row, arrayOfContainers ->
            arrayOfContainers.forEachIndexed { index, container ->
                if (container?.id == id) { slots[row][index] = null }
            }
        }
        return Bill(containerId = id, cost = 100)
    }

}
