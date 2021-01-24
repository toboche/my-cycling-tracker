package pl.toboche.mycyclingtracker.main.ui.service.date

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class CalendarServiceTest {

    val systemUnderTest = CalendarService()

    @Test
    fun `provide current date`() {
        val now = Calendar.getInstance()
        val expectedYear = now.get(Calendar.YEAR)
        val expectedMonth = now.get(Calendar.MONTH)
        val expectedDayOfMonth = now.get(Calendar.DAY_OF_MONTH)

        val actualValue = systemUnderTest.getNow()

        assertThat(actualValue.get(Calendar.YEAR))
            .isEqualTo(expectedYear)
        assertThat(actualValue.get(Calendar.MONTH))
            .isEqualTo(expectedMonth)
        assertThat(actualValue.get(Calendar.DAY_OF_MONTH))
            .isEqualTo(expectedDayOfMonth)
    }
}