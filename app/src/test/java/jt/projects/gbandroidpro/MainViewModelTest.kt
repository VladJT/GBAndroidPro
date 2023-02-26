package jt.projects.gbandroidpro

import jt.projects.gbandroidpro.presentation.ui.main.MainInteractorImpl
import jt.projects.gbandroidpro.presentation.ui.main.MainViewModel
import jt.projects.utils.network.INetworkStatus
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class MainViewModelTest {
    lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var interactor: MainInteractorImpl

    @Mock
    private lateinit var networkStatus: INetworkStatus

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(interactor, networkStatus)
    }

    @Test
    fun searchGitHub_Test() {
        val searchQuery = "some query"
        mainViewModel.getData(searchQuery)

        runBlocking {
            Mockito.verify(interactor, Mockito.times(1))
                .getData(searchQuery, networkStatus.isOnline())
        }
    }


}