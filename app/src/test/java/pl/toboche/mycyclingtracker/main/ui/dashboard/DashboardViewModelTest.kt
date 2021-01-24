package pl.toboche.mycyclingtracker.main.ui.dashboard

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import pl.toboche.mycyclingtracker.testing.getOrAwaitValue

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class DashboardViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `initial setup test`() {
        val dashboardViewModel = DashboardViewModel()

        Assertions.assertThat(dashboardViewModel.text.getOrAwaitValue())
            .isEqualTo("This is dashboard Fragment")
    }

}