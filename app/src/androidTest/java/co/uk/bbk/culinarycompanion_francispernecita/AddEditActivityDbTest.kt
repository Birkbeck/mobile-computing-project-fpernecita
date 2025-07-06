package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ActivityScenario
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
class AddEditActivityDbTest {
    private lateinit var db: RecipesDatabase
    private lateinit var dao: RecipesDao

    @Before
    fun setup() {
        // Initialize the Room database and DAO before each test
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Create an in-memory Room database for testing
        db = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.recipesDao()
    }

    @After
    fun teardown() {
        db.close()
    }
    // Test the insertion of a new recipe into the database
    @Test
    fun testInsertAndRetrieveRecipe() = runBlocking {
        val scenario = ActivityScenario.launch(AddEditActivity::class.java)
        scenario.onActivity { activity ->
            val viewModel = ViewModelProvider(activity)[RecipeViewModel::class.java]
            viewModel.recipesDao = dao
            // Create a new recipe
            viewModel.addRecipe(
                title = "Sinigang",
                category = "Soup",
                description = "Tamarind-based stew",
                ingredients = "Pork, vegetables, tamarind",
                instructions = "Boil and simmer"
            )
        }
        // Wait for the recipe to be inserted
        delay(200)

        val recipe = dao.getAllRecipes()
        assertTrue(recipe.any { it.title == "Sinigang" })
    }
}