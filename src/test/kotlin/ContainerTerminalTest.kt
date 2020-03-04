import com.nortal.dogo.terminal.Container
import com.nortal.dogo.terminal.ContainerTerminal
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ContainerTerminalTest {

    @Test
    fun `getFreeSpots returns 900 If Nothing Stored`() {
        val freeSpots = ContainerTerminal().getFreeSpots()

        assertThat(freeSpots).isEqualTo(900)
    }

    @Test
    fun `storeContainer should take one spot and decrease total free spots amount`() {
        val containerTerminal = ContainerTerminal()

        val container = Container()

        containerTerminal.storeContainer(container)
        val freeSpots = containerTerminal.getFreeSpots()

        assertThat(freeSpots).isEqualTo(899)
    }
}
