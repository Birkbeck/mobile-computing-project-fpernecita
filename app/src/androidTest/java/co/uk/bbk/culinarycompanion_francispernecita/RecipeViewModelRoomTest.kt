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
    }

    val result = viewModel.recipes.getOrAwaitValue()

    assertEquals(1, result.size)
    assertEquals("Tinola", result[0].title)
}