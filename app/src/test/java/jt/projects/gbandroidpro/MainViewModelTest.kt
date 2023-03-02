package jt.projects.gbandroidpro

import jt.projects.gbandroidpro.presentation.ui.main.MainInteractorImpl
import jt.projects.gbandroidpro.presentation.ui.main.MainViewModel
import jt.projects.model.data.APPSTATE_ERROR_EMPTY_DATA
import jt.projects.model.data.APPSTATE_SUCCESS
import jt.projects.utils.network.INetworkStatus
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertSame

@ExtendWith(
    MockitoExtension::class
)
class MainViewModelTest {

    private val mainViewModel: MainViewModel by lazy { MainViewModel(interactor, networkStatus) }

    @Mock(strictness = Mock.Strictness.LENIENT)
    private lateinit var interactor: MainInteractorImpl

    @Mock(strictness = Mock.Strictness.LENIENT)
    private lateinit var networkStatus: INetworkStatus

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val GOOD_QUERY = "correct query"
    private val BAD_QUERY = "some incorrect query"

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)

        `when`(networkStatus.isOnline()).thenReturn(true)
        runBlocking {
            `when`(interactor.getData(BAD_QUERY, networkStatus.isOnline())).thenReturn(
                APPSTATE_ERROR_EMPTY_DATA
            )
            `when`(interactor.getData(GOOD_QUERY, networkStatus.isOnline())).thenReturn(
                APPSTATE_SUCCESS
            )
        }
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun mainViewModel_getDataFromInteractor() {
        val response = runBlocking {
            mainViewModel.getDataFromInteractor(GOOD_QUERY, networkStatus.isOnline())
        }
        runBlocking { verify(interactor, times(1)).getData(GOOD_QUERY, networkStatus.isOnline()) }
        assertSame(APPSTATE_SUCCESS, response)
    }

    @Test
    fun mainViewModel_handleError() {
        val response = runBlocking {
            mainViewModel.getDataFromInteractor(BAD_QUERY, networkStatus.isOnline())
        }

        assertSame(APPSTATE_ERROR_EMPTY_DATA, response)
    }

    @Test
    fun mainViewModel_checkRightOrder() {
        runBlocking {
//            val searchQuery = "some query"
//            `when`(networkStatus.isOnline()).thenReturn(true)
//            `when`(interactor.getData(searchQuery, true)).thenAnswer {
//                APPSTATE_SUCCESS
//            }
//
//
//
//            //Определяем порядок вызова методов какого класса мы хотим проверить
//            val inOrder = inOrder(interactor)
//
//            //Прописываем порядок вызова методов
//            verify(interactor).getData(searchQuery, true)
        }
    }
}