package jt.projects.gbandroidpro


import jt.projects.gbandroidpro.di.repoModule
import jt.projects.model.data.DataModel
import jt.projects.repository.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertTrue


class RemoteRepoTest : KoinTest {

    private val repo by inject<Repository<Flow<DataModel>>>()

    private lateinit var correctResponse: List<DataModel>
    private lateinit var emptyResponse: List<DataModel>

    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(repoModule) }

    @Before
    fun before() {
        runBlocking {
            correctResponse = GlobalScope.async {
                repo.getDataByWord("loaf").toList()
            }.await()

            emptyResponse = GlobalScope.async {
                repo.getDataByWord("gggg").toList()
            }.await()
        }
    }

    companion object {
        @AfterClass
        fun after() {
            stopKoin()
        }
    }

    @Test
    fun remoteSource_CorrectWordReturnsNotEmpty() {
        assertTrue(correctResponse.isNotEmpty())
    }


    @Test
    fun remoteSource_CorrectWordReturnsCorrectData() {
        assertTrue(correctResponse.size == 8 && correctResponse[0].meanings.equals("буханка, буханка, бездельничать, бездельничать"))
    }

    @Test
    fun remoteSource_BadWordReturnsReturnEmptyResponse() {
        assertTrue(emptyResponse.isEmpty())
    }

//    @Test
//    fun remoteSource_correctTimeout() {
//        runBlocking {
//            GlobalScope.async {
//                delay(2000)
//                repo.getDataByWord("test").toList()
//            }.await()
//          // Assertions.
//        }
//    }


}