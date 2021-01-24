package pl.toboche.mycyclingtracker.main.ui.home

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import pl.toboche.mycyclingtracker.main.ui.service.date.CalendarService
import pl.toboche.mycyclingtracker.testing.getOrAwaitValue
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class AddNewRecordViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val mockNow = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2018)
        set(Calendar.MONTH, 8)
        set(Calendar.DAY_OF_MONTH, 22)
    }
    val mockCalendarService: CalendarService = mock {
        on { getNow() } doReturn mockNow
    }
    val systemUnderTest = AddNewRecordViewModel(mockCalendarService)

    @Test
    fun `initial date set to today`() {
        assertThat(systemUnderTest.dateText.getOrAwaitValue())
            .isEqualTo("Sep 22, 2018")
    }

    @Test
    fun `update date description date changes`() {
        systemUnderTest.setDate(2010, 2, 12)

        assertThat(systemUnderTest.dateText.getOrAwaitValue())
            .isEqualTo("Mar 12, 2010")
    }
}