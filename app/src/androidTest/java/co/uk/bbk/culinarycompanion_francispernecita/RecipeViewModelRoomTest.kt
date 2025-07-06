package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.room.Room
// lifecycle testing
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
// courtines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest

import co.uk.bbk.culinarycompanion_francispernecita.Recipe
import co.uk.bbk.culinarycompanion_francispernecita.RecipesDao
import co.uk.bbk.culinarycompanion_francispernecita.RecipesDatabase
import co.uk.bbk.culinarycompanion_francispernecita.RecipeViewModel

// JUnit
import org.junit.*
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModelRoomTest {
    //
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: RecipesDatabase
    private lateinit var dao: RecipesDao
    private lateinit var viewModel: RecipeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Initialize the Room database and DAO before each test
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.recipesDao()
        viewModel = RecipeViewModel()
        viewModel.recipesDao = dao
    }

    @After
    fun teardown() {
        db.close()
        Dispatchers.resetMain()
    }

    @Test
    fun testDatabaseInsertsAndUpdatesLiveData() = run test {
        viewModel.addRecipe(
            title = "Tinola",
            category = "Soup",
            description = "Ginger chicken broth",
            ingredients = "Chicken, ginger, green papaya",
            instructions = "Boil and simmer"
        )
        //
        testDispatcher.scheduler.advanceUntilIdle()

        // wait for the LiveData to update
        val result = viewModel.recipes.getOrAwaitValue()

        assertEquals(1, result.size)
        assertEquals("Tinola", result[0].title)
    }
    // Helper for LiveData testing - source: https://gist.github.com/JoseAlcerreca/35828c25fca123c8a115d6251cf3f45b
    private fun <T> LiveData<T>.getOrAwaitValue(): T {
        var data: T? = null
        val latch = CountDownLatch(1)

        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        return data ?: throw NullPointerException("LiveData value was null")
    }
}