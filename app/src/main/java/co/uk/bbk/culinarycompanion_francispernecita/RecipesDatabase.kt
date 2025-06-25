package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1)
abstract class RecipesDatabase : RoomDatabase() {
}