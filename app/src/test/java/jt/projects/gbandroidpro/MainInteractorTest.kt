package jt.projects.gbandroidpro


import jt.projects.gbandroidpro.presentation.ui.main.MainInteractorImpl
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.model.data.EMPTY_RESPONSE_EXCEPTION
import jt.projects.model.data.TEST_RESPONSE_SUCCESS
import jt.projects.repository.RepositoryImpl
import jt.projects.repository.RepositoryLocal
import jt.projects.repository.retrofit.RetrofitImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.system.measureTimeMillis

/**
 * JUNIT 5
 */

@ExtendWith(MockitoExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MainInteractorTest : KoinTest {
    companion object {
        const val TIMEOUT_RESPONSE = 600L
    }

    @get:Rule
    var testCoroutineRule = MainDispatcherRule()

    private val remoteRepo: RepositoryImpl by inject()

    @Mock
    lateinit var localRepo: RepositoryLocal<Flow<DataModel>>

    private val mainInteractorImpl by lazy {
        MainInteractorImpl(
            repositoryRemote = remoteRepo,
            repositoryLocal = localRepo
        )
    }

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single { RepositoryImpl(RetrofitImpl()) }
            })
    }


    private fun getData(word: String): List<DataModel> =
        runBlocking {
            remoteRepo.getDataByWord(word).toList()
        }


    @BeforeEach
    fun before() {
        MockitoAnnotations.openMocks(this)
    }


    /***
     * TEST REPOSITORY
     */
    @Nested
    inner class `TEST REPOSITORY` {

        @ParameterizedTest
        @CsvSource("load, true", "bread, true", "ggg, false", "repooooo, false")
        @DisplayName("Check Response-NotEmpty from Remote Source")
        fun remoteSource_CorrectWordReturnsNotEmpty(word: String, expected: Boolean) {
            val result = getData(word)

            assertNotNull(result)
            assertEquals(result.isNotEmpty(), expected)
        }

        @Test
        @DisplayName("Check Response-Data from Remote Source")
        fun remoteSource_CorrectWordReturnsCorrectData() {
            val wordKey = "loaf"
            val result = getData(wordKey)
            assertTrue(result.size == expectedSize[wordKey])

            val expected =
                expectedData[wordKey]!!.meanings.split(", ").toTypedArray()
            val meanings = result[0].meanings.split(", ").toTypedArray()

            assertArrayEquals(expected, meanings)
        }

        @ParameterizedTest
        @CsvSource(
            "go, $TIMEOUT_RESPONSE",
            "main,  $TIMEOUT_RESPONSE",
            "test,  $TIMEOUT_RESPONSE",
            "bbbb,  $TIMEOUT_RESPONSE"
        )
        fun remoteSource_CheckTimeoutResponse(someWord: String, responseTime: Long) {
            val time = measureTimeMillis {
                getData(someWord)
            }
            if (time > responseTime) throw IllegalStateException("Timeout response $responseTime")
        }
    }

    /***
     * TEST MAIN INTERACTOR
     */
    @Nested
    inner class `TEST MAIN_INTERACTOR` {

        @Test
        fun mainInteractor_fromRemoteSourceReturnCorrectData() = runTest {
            val wordKey = "go"
            val result = mainInteractorImpl.getData(wordKey, fromRemoteSource = true)
            assertTrue(result is AppState.Success)
            val data = (result as AppState.Success).data!!
            assertTrue(data.size == 15)
            val expected =
                expectedData[wordKey]!!.meanings.split(", ").toTypedArray()
            val meanings = data[0].meanings.split(", ").toTypedArray()
            assertArrayEquals(expected, meanings)
        }

        @Test
        fun mainInteractor_fromRemoteSourceReturnNoData() = runTest {
            val wordKey = "gggg"
            val result = mainInteractorImpl.getData(wordKey, fromRemoteSource = true)

            assertTrue(result is AppState.Error)
            val error = (result as AppState.Error).error
            assertSame(EMPTY_RESPONSE_EXCEPTION, error)
        }

        @Test
        fun mainInteractor_fromLocalSourceReturnCorrectData() = runTest {
            val wordKey = "go"
            `when`(localRepo.getDataByWord(wordKey)).thenAnswer { TEST_RESPONSE_SUCCESS }
            mainInteractorImpl.getData(wordKey, false)

            assertEquals(localRepo.getDataByWord(wordKey), TEST_RESPONSE_SUCCESS)
            Mockito.verify(localRepo, Mockito.times(2)).getDataByWord(wordKey)
        }
    }
}