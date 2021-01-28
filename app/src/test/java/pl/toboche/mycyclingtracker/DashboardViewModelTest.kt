package pl.toboche.mycyclingtracker

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import pl.toboche.mycyclingtracker.data.source.TrackRecordRepository
import pl.toboche.mycyclingtracker.data.source.local.Result
import pl.toboche.mycyclingtracker.data.source.local.TrackRecord
import pl.toboche.mycyclingtracker.testing.getOrAwaitValue
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class DashboardViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    val mockTrackRecordRepository: TrackRecordRepository = mock<TrackRecordRepository>().apply {
        runBlocking {
            whenever(getTrackRecords()).doReturn(Result.Success(emptyList()))
        }
    }
    val mockDate = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2018)
        set(Calendar.MONTH, 8)
        set(Calendar.DAY_OF_MONTH, 22)
    }
    val mockStringDate = "Sep 22, 2018"
    val mockTitle = "title"
    val mockComments = "comments"
    val mockDoubleDistance = 1.2
    val mockDistance = "1.2"

    val systemUnderTest = DashboardViewModel(
        mockTrackRecordRepository
    )

    @Test
    fun `load empty list properly`() {
        systemUnderTest.loadTrackRecords()

        assertThat(systemUnderTest.trackRecords.getOrAwaitValue())
            .isEmpty()
        assertThat(systemUnderTest.emptyText.getOrAwaitValue())
            .isEqualTo("no items to show")
    }

    @Test
    fun `map data properly`() {
        givenRepositoryReturnsSingleValue()

        systemUnderTest.loadTrackRecords()

        assertThat(systemUnderTest.trackRecords.getOrAwaitValue())
            .containsExactly(
                DashboardViewModel.TrackRecordItem(
                    mockTitle,
                    mockComments,
                    mockDistance,
                    mockStringDate
                )
            )
        assertThat(systemUnderTest.emptyText.getOrAwaitValue())
            .isNull()
    }

    @Test
    fun `show error when loading data`() {
        val expectedErrorText = "error loading data"
        givenRepositoryReturnsError()

        systemUnderTest.loadTrackRecords()

        assertThat(systemUnderTest.error.getOrAwaitValue())
            .isEqualTo(expectedErrorText)
    }

    private fun givenRepositoryReturnsError() {
        runBlocking {
            whenever(mockTrackRecordRepository.getTrackRecords(anyOrNull())).thenReturn(
                Result.Error(Exception())
            )
        }
    }

    private fun givenRepositoryReturnsSingleValue() {
        runBlocking {
            whenever(mockTrackRecordRepository.getTrackRecords(anyOrNull())).thenReturn(
                Result.Success(
                    listOf(
                        TrackRecord(
                            title = mockTitle,
                            comments = mockComments,
                            distance = mockDoubleDistance,
                            date = mockDate.time
                        )
                    )
                )
            )
        }
    }
}