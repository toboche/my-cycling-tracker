package pl.toboche.mycyclingtracker.home

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import pl.toboche.mycyclingtracker.data.source.TrackRecordRepository
import pl.toboche.mycyclingtracker.data.source.local.TrackRecord
import pl.toboche.mycyclingtracker.service.date.CalendarService
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
    val mockTrackRecordRepository: TrackRecordRepository = mock()

    val systemUnderTest = AddNewRecordViewModel(
        mockCalendarService,
        mockTrackRecordRepository
    )

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

    @Test
    fun `does not save when title empty`() {
        systemUnderTest.save()

        verifyZeroInteractions(mockTrackRecordRepository)
    }

    @Test
    fun `show error when empty title`() {
        systemUnderTest.save()

        assertThat(systemUnderTest.errorText.getOrAwaitValue())
            .isEqualTo("please provide a title")
    }

    @Test
    fun `show error when empty distance`() {
        systemUnderTest.name.value = "some title"

        systemUnderTest.save()

        assertThat(systemUnderTest.errorText.getOrAwaitValue())
            .isEqualTo("please provide distance")
    }

    @Test
    fun `does not clear comments when title empty`() {
        val expectedComments = "expected comments"

        systemUnderTest.comments.value = expectedComments

        systemUnderTest.save()

        verifyZeroInteractions(mockTrackRecordRepository)
        assertThat(systemUnderTest.comments.value).isEqualTo(expectedComments)
    }

    @Test
    fun `save new record when all data provided`() {
        val expectedName = "expected name"
        val expectedComments = "expected comments"
        val expectedDistance = "1.1"
        val expectedDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2010)
            set(Calendar.MONTH, 2)
            set(Calendar.DAY_OF_MONTH, 12)
        }
        systemUnderTest.setDate(2010, 2, 12)
        systemUnderTest.name.value = expectedName
        systemUnderTest.comments.value = expectedComments
        systemUnderTest.distance.value = expectedDistance
        systemUnderTest.setDate(2010, 2, 12)

        systemUnderTest.save()

        runBlocking {
            verify(mockTrackRecordRepository).saveTrackRecord(
                argThat {
                    this.uid == null
                            && this.title == expectedName
                            && this.comments == expectedComments
                            && this.date.year == expectedDate.time.year
                            && this.date.month == expectedDate.time.month
                            && this.date.day == expectedDate.time.day
                            && this.distance == 1.1
                }
            )
        }
    }

    @Test
    fun `clear input data when saving`() {
        val expectedName = "expected name"
        val expectedComments = "expected comments"
        val expectedDistance = "1.1"
        systemUnderTest.setDate(2010, 2, 12)
        systemUnderTest.name.value = expectedName
        systemUnderTest.comments.value = expectedComments
        systemUnderTest.distance.value = expectedDistance

        systemUnderTest.save()

        runBlocking {
            verify(mockTrackRecordRepository).saveTrackRecord(
                TrackRecord(
                    uid = null,
                    title = expectedName,
                    comments = expectedComments,
                    date = mockNow.time,
                    distance = 1.1
                )
            )
        }
        assertThat(systemUnderTest.comments.value).isEqualTo("")
        assertThat(systemUnderTest.name.value).isEqualTo("")
        assertThat(systemUnderTest.distance.value).isEqualTo("")
    }
}