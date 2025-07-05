package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
// JUnit
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertTrue
// Coroutines, Room
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import androidx.room.Room

@RunWith(AndroidJUnit4::class)
class MainActivityViewModelTest {
    private lateinit var db: RecipesDatabase
    private lateinit var dao: RecipesDao
    // setup the database before each test
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.recipesDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testAddRecipeThroughViewModel() = runBlocking {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            val viewModel = ViewModelProvider(activity).get(RecipeViewModel::class.java)
            viewModel.recipesDao = dao

            viewModel.addRecipe(
                title = "Sinigang",
                category = "Soup",
                description = "Tamarind-based stew",
                ingredients = "Pork, vegetables, tamarind",
                instructions = "Boil and simmer"
            )
        }
        delay(200)

        val recipe = dao.getAllRecipes()
        assertTrue(recipe.any { it.title == "Sinigang" })
    }
}
