package co.uk.bbk.culinarycompanion_francispernecita

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel: ViewModel() {
    private val _recipes = MutableLiveData(listOf<Recipe>())
    val recipes: LiveData<List<Recipe>> = _recipes

    var recipesDao: RecipesDao? = null
    // Read all recipes from the database - used a template from the week 8 lecture
    fun readAllRecipes() {
        viewModelScope.launch {
            recipesDao?.let {
                val recipes = it.getAllRecipes()

                Log.i("Recipe", recipes.toString())
                _recipes.value = recipes
            }
        }
    }
    // Add a new recipe to the database
    fun addRecipe(title: String, category: String, description: String, ingredients: String, instructions: String) {
        viewModelScope.launch {
            recipesDao?.let {
                val recipe = Recipe(
                    title = title,
                    category = category,
                    description = description,
                    ingredients = ingredients,
                    instructions = instructions
                )
                it.insertRecipe(recipe)

                readAllRecipes()
            }
        }
    }
    // Update an existing recipe in the database
    fun editRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipesDao?.let {
                it.updateRecipe(recipe)

                readAllRecipes()
            }
        }
    }
    // Delete a recipe from the database
    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipesDao?.let {
                it.deleteRecipe(recipe)

                readAllRecipes()
            }
        }
    }

    fun observeRecipeById(recipeId: Long): LiveData<Recipe> {
        return recipesDao?.observeRecipeById(recipeId)
    }

    data class RecipeCategory(
        val category: String,
        val recipes: List<Recipe>
    )
    // Group recipes by category
    companion object {
        fun groupRecipesByCategory(recipes: List<Recipe>): List<RecipeCategory> {
            return recipes
                .groupBy { it.category }
                .map { (category, recipesInCategory) ->
                    RecipeCategory(category, recipesInCategory)
                }
                .sortedBy { it.category }
        }
    }
}