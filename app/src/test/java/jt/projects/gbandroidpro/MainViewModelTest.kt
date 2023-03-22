package jt.projects.gbandroidpro

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import jt.projects.gbandroidpro.presentation.ui.main.MainInteractorImpl
import jt.projects.gbandroidpro.presentation.ui.main.MainViewModel
import jt.projects.model.data.APPSTATE_ERROR_EMPTY_DATA
import jt.projects.model.data.APPSTATE_SUCCESS
import jt.projects.model.data.AppState
import jt.projects.tests.BAD_QUERY
import jt.projects.tests.GOOD_QUERY
import jt.projects.utils.network.INetworkStatus
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
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

/**
 * JUNIT 4
 */

@ExtendWith(
    MockitoExtension::class
)
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    /**
     * InstantTaskExecutorRule — это JUnit Rule. Так называемое Правило. Правила используются
    через аннотации и позволяют запускать нужный вам код до проведения теста и после (по аналогии с
    @Before и @After)
     */
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()


    private lateinit var mainViewModel: MainViewModel

    @Mock(strictness = Mock.Strictness.LENIENT)
    private lateinit var interactor: MainInteractorImpl

    @Mock(strictness = Mock.Strictness.LENIENT)
    private lateinit var networkStatus: INetworkStatus

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        Dispatchers.setMain(mainThreadSurrogate)

        mainViewModel = MainViewModel(interactor, networkStatus)

        `when`(networkStatus.isOnline()).thenReturn(true)

        testCoroutineRule.runBlockingTest {
            `when`(interactor.getData(BAD_QUERY, networkStatus.isOnline()))
                .thenReturn(APPSTATE_ERROR_EMPTY_DATA)

            `when`(interactor.getData(GOOD_QUERY, networkStatus.isOnline()))
                .thenReturn(APPSTATE_SUCCESS)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun mainViewModel_getDataFromInteractor() {
        val response = testCoroutineRule.getResult {
            mainViewModel.getDataFromInteractor(GOOD_QUERY, networkStatus.isOnline())
        }
        testCoroutineRule.runBlockingTest {
            verify(interactor, times(1)).getData(GOOD_QUERY, networkStatus.isOnline())
        }
        assertEquals(APPSTATE_SUCCESS, response)
    }

    @Test
    fun mainViewModel_handleError() {
        val observer = Observer<AppState> {}
        val liveData = mainViewModel.liveDataForViewToObserve

        liveData.observeForever(observer)

        mainViewModel.loadData(BAD_QUERY)

        Thread.sleep(2000)//чтобы корутина успела отработать все стадии AppState

        //Убеждаемся, что Репозиторий вернул данные и LiveData передала их Наблюдателям
        assertNotNull(liveData.value)
        assertEquals(APPSTATE_ERROR_EMPTY_DATA, liveData.value)

        liveData.removeObserver(observer)
    }

    @Test
    fun liveData_testGetData() {
        val observer = Observer<AppState> {}
        val liveData = mainViewModel.liveDataForViewToObserve
        liveData.observeForever(observer)

        mainViewModel.loadData(GOOD_QUERY)

        Thread.sleep(2000)//чтобы корутина успела отработать все стадии AppState

        //Убеждаемся, что Репозиторий вернул данные и LiveData передала их Наблюдателям
        assertNotNull(liveData.value)
        assertEquals(APPSTATE_SUCCESS, liveData.value)

        liveData.removeObserver(observer)
    }
}