import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nortal.dogo.terminal.Bill
import com.nortal.dogo.terminal.Container
import com.nortal.dogo.terminal.ContainerTerminal
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class ContainerTerminalTest {

    @Test
    fun `getFreeSpots returns 900 If Nothing Stored`() {
        val freeSpots = ContainerTerminal().getFreeSlots()

        assertThat(freeSpots).isEqualTo(900)
    }

    @Test
    fun `storeContainer should take one spot and decrease total free spots amount`() {
        val containerTerminal = ContainerTerminal()

        val container = Container()

        containerTerminal.storeContainer(container)
        val freeSpots = containerTerminal.getFreeSlots()

        assertThat(freeSpots).isEqualTo(899)
    }

    @Test
    fun `removeContainer returns a calculated bill after one day`() {
        val containerTerminal = ContainerTerminal()

        val container = Container()
        containerTerminal.storeContainer(container)

        val bill = containerTerminal.removeContainer(container.id)

        assertThat(bill).isEqualTo(Bill(containerId = container.id, cost = 100))
        assertThat(containerTerminal.getFreeSlots()).isEqualTo(900)
    }

    @Test
    fun `removeContainer returns a calculated bill after day and 2 hours`() {
        val now = Instant.parse("2020-03-03T03:03:03Z")
        val clockMock = mock<Clock> {
            on(it.instant()) doReturn now doReturn now.plus(26, ChronoUnit.HOURS)
            on(it.zone) doReturn ZoneId.systemDefault()
        }

        val container = Container()
        val containerTerminal = ContainerTerminal(clockMock)
        containerTerminal.storeContainer(container)

        val bill = containerTerminal.removeContainer(container.id)

        assertThat(bill).isEqualTo(Bill(containerId = container.id, cost = 200))
        assertThat(containerTerminal.getFreeSlots()).isEqualTo(900)
    }

}
