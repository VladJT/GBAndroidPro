package jt.projects.gbandroidpro

import androidx.lifecycle.MutableLiveData
import jt.projects.gbandroidpro.presentation.ui.main.MainInteractorImpl
import jt.projects.gbandroidpro.presentation.ui.main.MainViewModel
import jt.projects.model.data.APPSTATE_ERROR_EMPTY_DATA
import jt.projects.model.data.APPSTATE_SUCCESS
import jt.projects.model.data.AppState
import jt.projects.utils.network.INetworkStatus
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertSame

@ExtendWith(
    MockitoExtension::class
)
class MainViewModelTest {

    private val mainViewModel: MainViewModel by lazy { MainViewModel(interactor, networkStatus) }

    @Mock
    private lateinit var interactor: MainInteractorImpl

    @Mock
    private lateinit var networkStatus: INetworkStatus

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
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
            `when`(networkStatus.isOnline()).thenReturn(true)

            CoroutineScope(mainThreadSurrogate).launch {
                mainViewModel.getDataFromInteractor(
                    searchQuery,
                    networkStatus.isOnline()
                )
            }

            verify(interactor, Mockito.times(1))
                .getData(searchQuery, networkStatus.isOnline())
        }
    }

    @Test
    fun mainViewModel_handleError() {
        val searchQuery = "some error query" //error
        val response = runBlocking {
            `when`(networkStatus.isOnline()).thenReturn(true)
            `when`(interactor.getData(searchQuery, networkStatus.isOnline())).thenReturn(
                APPSTATE_ERROR_EMPTY_DATA
            )

            mainViewModel.getDataFromInteractor(searchQuery, networkStatus.isOnline())
        }

        assertSame(APPSTATE_ERROR_EMPTY_DATA, response)
    }

    @Test
    fun mainViewModel_checkRightOrder() {
        runBlocking {
            val searchQuery = "some query"
            val liveData: MutableLiveData<AppState> = MutableLiveData()

            val vm = mock(MainViewModel::class.java)

            `when`(vm.getDataFromInteractor(searchQuery, true)).thenReturn(APPSTATE_SUCCESS)

            vm.loadData(searchQuery)

            //Определяем порядок вызова методов какого класса мы хотим проверить
            val inOrder = inOrder(vm)

            //Прописываем порядок вызова методов
            inOrder.verify(vm).showProgress()
            inOrder.verify(vm).handleResponse(APPSTATE_SUCCESS)
        }


    }

}