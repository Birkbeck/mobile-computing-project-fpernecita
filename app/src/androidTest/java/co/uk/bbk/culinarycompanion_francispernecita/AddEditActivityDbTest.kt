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
// App classes
import co.uk.bbk.culinarycompanion_francispernecita.Recipe
import co.uk.bbk.culinarycompanion_francispernecita.RecipesDao
import co.uk.bbk.culinarycompanion_francispernecita.RecipesDatabase

@RunWith(AndroidJUnit4::class)
class AddEditActivityDbTest {
    private lateinit var db: RecipesDatabase
    private lateinit var dao: RecipesDao
}