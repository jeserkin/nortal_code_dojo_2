package com.nortal.dogo.terminal

class ContainerTerminal {

    private val slots: Array<Array<Container?>> = Array(30) { Array<Container?>(30) { null } }

    fun getFreeSpots(): Int {
        return 900;
    }

    fun storeContainer(container: Container) {
        slots[0][0] = container
    }

}
