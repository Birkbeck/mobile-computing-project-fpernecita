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

class RecipeViewModelRoomTest {

}