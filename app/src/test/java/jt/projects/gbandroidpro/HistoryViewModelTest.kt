package jt.projects.gbandroidpro

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import jt.projects.gbandroidpro.presentation.ui.history.HistoryInteractorImpl
import jt.projects.gbandroidpro.presentation.ui.history.HistoryViewModel
import jt.projects.model.data.APPSTATE_ERROR_EMPTY_DATA
import jt.projects.model.data.APPSTATE_SUCCESS
import jt.projects.model.data.AppState
import jt.projects.tests.BAD_QUERY
import jt.projects.tests.GOOD_QUERY
import jt.projects.utils.network.INetworkStatus
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(
    MockitoExtension::class
)
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class HistoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()


    private lateinit var historyViewModel: HistoryViewModel

    @Mock
    private lateinit var interactor: HistoryInteractorImpl

    @Mock(strictness = Mock.Strictness.LENIENT)
    private lateinit var networkStatus: INetworkStatus

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        historyViewModel = HistoryViewModel(interactor, networkStatus)
        `when`(networkStatus.isOnline()).thenReturn(true)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun prepareGoodAnswer() = testCoroutineRule.runBlockingTest {
        `when`(interactor.getAllData())
            .thenReturn(APPSTATE_SUCCESS) // default
    }

    private fun prepareBadAnswer() = testCoroutineRule.runBlockingTest {
        `when`(interactor.getAllData())
            .thenReturn(APPSTATE_ERROR_EMPTY_DATA) // default
    }

    @Test
    fun historyViewModel_getDataFromInteractor() {
        prepareGoodAnswer()

        historyViewModel.getData(GOOD_QUERY)

        testCoroutineRule.runBlockingTest {
            verify(interactor, times(1)).getAllData()
        }
    }

    @Test
    fun historyViewModel_handleError() {
        prepareBadAnswer()

        val observer = Observer<AppState> {}
        val liveData = historyViewModel.liveDataForViewToObserve

        historyViewModel.liveDataForViewToObserve.observeForever(observer)

        historyViewModel.getData(BAD_QUERY)

        //Убеждаемся, что Репозиторий вернул данные и LiveData передала их Наблюдателям
        assertNotNull(liveData.value)
        assertEquals(APPSTATE_ERROR_EMPTY_DATA, liveData.value)

        liveData.removeObserver(observer)
    }

    @Test
    fun liveData_testGetData() {
        prepareGoodAnswer()

        val observer = Observer<AppState> {}
        val liveData = historyViewModel.liveDataForViewToObserve

        liveData.observeForever(observer)

        historyViewModel.getData(GOOD_QUERY)

        assertNotNull(liveData.value)
        assertEquals(APPSTATE_SUCCESS, liveData.value)

        liveData.removeObserver(observer)
    }

}