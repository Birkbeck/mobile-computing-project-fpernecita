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

        fun getDatabase(context: Context): RecipesDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecipesDatabase::class.java,
                    "recipes_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}