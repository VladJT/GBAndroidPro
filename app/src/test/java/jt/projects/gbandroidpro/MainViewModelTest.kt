package jt.projects.gbandroidpro

import jt.projects.gbandroidpro.presentation.ui.main.MainInteractorImpl
import jt.projects.gbandroidpro.presentation.ui.main.MainViewModel
import jt.projects.model.data.APPSTATE_ERROR_EMPTY_DATA
import jt.projects.utils.network.INetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertSame

@ExtendWith(
    MockitoExtension::class
)
class MainViewModelTest {

    lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var interactor: MainInteractorImpl

    @Mock
    private lateinit var networkStatus: INetworkStatus

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        mainViewModel = MainViewModel(interactor, networkStatus)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun mainViewModel_getDataFromInteractor() {
        val searchQuery = "some query"
        runBlocking {
            Mockito.`when`(networkStatus.isOnline()).thenReturn(true)
            mainViewModel.getDataFromInteractor(searchQuery, networkStatus.isOnline())

            Mockito.verify(interactor, Mockito.times(1))
                .getData(searchQuery, networkStatus.isOnline())
        }
    }

    @Test
    fun mainViewModel_handleError() {
        val searchQuery = "some error query" //error
        val result = runBlocking {
            Mockito
                .`when`(interactor.getData(searchQuery,true))
                .thenReturn(APPSTATE_ERROR_EMPTY_DATA)
            mainViewModel.getDataFromInteractor(searchQuery, true)
        }
        assertSame(result, APPSTATE_ERROR_EMPTY_DATA)
    }

}