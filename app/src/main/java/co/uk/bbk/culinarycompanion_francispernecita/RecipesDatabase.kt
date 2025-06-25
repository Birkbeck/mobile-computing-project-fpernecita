package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// code reused from the week 8 lecture
@Database(entities = [Recipe::class], version = 1)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao

    companion object {
        @Volatile
        private var INSTANCE: RecipesDatabse? = null
    }
}