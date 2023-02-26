package jt.projects.gbandroidpro


import jt.projects.gbandroidpro.di.repoModule
import jt.projects.gbandroidpro.presentation.ui.main.MainInteractorImpl
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.repository.Repository
import jt.projects.repository.RepositoryLocal
import junit.framework.TestCase.assertEquals
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.junit.AfterClass
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.system.measureTimeMillis
import kotlin.test.assertNotNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

const val SUCCESS_FROM_LOCAL_REPO = "Success"


@RunWith(JUnitParamsRunner::class)
class RemoteRepoTest : KoinTest {

    private val remoteRepo by inject<Repository<Flow<DataModel>>>()

    @Mock
    lateinit var localRepo: RepositoryLocal<Flow<DataModel>>

    private val mainInteractorImpl by lazy {
        MainInteractorImpl(
            repositoryRemote = remoteRepo,
            repositoryLocal = localRepo
        )
    }


    private fun getData(word: String): List<DataModel> =
        runBlocking {
            remoteRepo.getDataByWord(word).toList()
        }


    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(repoModule) }

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    companion object {
        @AfterClass
        fun after() {
            stopKoin()
        }
    }

    @Test
    @Parameters(value = ["load, true", "bread, true", "ggg, false", "repooooo, false"])
    fun remoteSource_CorrectWordReturnsNotEmpty(word: String, expected: Boolean) {
        val result = getData(word)

        assertNotNull(result)
        assertEquals(result.isNotEmpty(), expected)
    }

    @Test
    fun remoteSource_CorrectWordReturnsCorrectData() {
        val result = getData("loaf")
        assertTrue(result.size == 8)

        val expected =
            "буханка, буханка, бездельничать, бездельничать".split(", ").toTypedArray()
        val meanings = result[0].meanings.split(", ").toTypedArray()

        assertArrayEquals(meanings, expected)
    }

    @Test
    @Parameters(value = ["go, 500", "main, 500", "test, 500", "bbbb, 500"])
    fun remoteSource_CheckTimeoutResponse(someWord: String, responseTime: Long) {
        val time = measureTimeMillis {
            getData(someWord)
        }
        if (time > responseTime) throw IllegalStateException("Timeout response $responseTime")
    }

    @Test
    fun mainInteractor_fromRemoteSourceReturnCorrectData() {
        val result =
            runBlocking {
                mainInteractorImpl.getData("go", fromRemoteSource = true)
            }

        assertTrue(result is AppState.Success)
        assertTrue(result.data!!.size == 15)

        val expected =
            "идти, ходить, вести, проходить, становиться, проходить, умирать, исчезать, заканчиваться, (исправно) работать, сочетаться, говорить, издавать звук, помещаться, попытка"
                .split(", ").toTypedArray()
        val meanings = result.data!![0].meanings.split(", ").toTypedArray()

        assertArrayEquals(meanings, expected)
    }

    @Test
    fun mainInteractor_fromRemoteSourceReturnNoData() {
        val result =
            runBlocking {
                mainInteractorImpl.getData("gggg", fromRemoteSource = true)
            }


        assertTrue(result is AppState.Error)
        assertSame(result.error.message, Throwable("Перевод не найден").message)
    }

    @Test
    fun mainInteractor_fromLocalSourceReturnCorrectData() {
        runBlocking {
            `when`(localRepo.getDataByWord(word = "go")).thenAnswer { SUCCESS_FROM_LOCAL_REPO }
            assertEquals(localRepo.getDataByWord("go"), SUCCESS_FROM_LOCAL_REPO)
        }
    }

}