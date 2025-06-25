package co.uk.bbk.culinarycompanion_francispernecita

import androidx.room.Entity
import androidx.room.PrimaryKey
 // Recipe entity class representing the recipe table in the database

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val category: String,
    val description: String,
    val ingredients: String,
    val instructions: String
):Serializable