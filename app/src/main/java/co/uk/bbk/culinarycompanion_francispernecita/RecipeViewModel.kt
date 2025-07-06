package co.uk.bbk.culinarycompanion_francispernecita

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel: ViewModel() {
    // LiveData for the list of recipes, mutable version only exposed to the ViewModel
    private val _recipes = MutableLiveData(listOf<Recipe>())
    // Expose read-only version of the LiveData
    val recipes: LiveData<List<Recipe>> = _recipes
    // DAO for database operations needs initalisation
    var recipesDao: RecipesDao? = null
    // Fetch all recipes from the database - used a template from the week 8 lecture
    fun readAllRecipes() {
        viewModelScope.launch {
            recipesDao?.let {
                val recipes = it.getAllRecipes()

                // Log.i("Recipe", recipes.toString())
                // _recipes.value = recipes
                _recipes.postValue(recipes.map { it.copy() })
            }
        }
    }
    // Insert a new recipe to the database using parameters from the UI and refresh the list
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

//    // Add a new recipe to the database and return its ID
//    suspend fun addRecipeAndReturnId(recipe: Recipe): Long {
//        return recipesDao?.insertRecipe(recipe) ?: -1
//    }

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
    // Get a specific recipe by its ID
    fun observeRecipeById(recipeId: Long): LiveData<Recipe>? {
        return recipesDao?.observeRecipeById(recipeId)
    }

    // sample insert recipe function
    fun insertSampleRecipes() {
        viewModelScope.launch {
            recipesDao?.let { dao ->
                val sampleRecipes = listOf(
                    Recipe(title = "Pancakes", category = "Breakfast", description = "Fluffy and golden", ingredients = "Flour, Milk, Eggs", instructions = "Mix, cook, serve."),
                    Recipe(title = "Avocado Toast", category = "Brunch", description = "Healthy and simple", ingredients = "Bread, Avocado, Salt", instructions = "Toast, mash, season."),
                    Recipe(title = "Grilled Cheese", category = "Lunch", description = "Cheesy and toasty", ingredients = "Bread, Cheese, Butter", instructions = "Grill until golden."),
                    Recipe(title = "Spaghetti Bolognese", category = "Dinner", description = "Hearty Italian classic", ingredients = "Pasta, Beef, Tomato Sauce", instructions = "Cook pasta, simmer sauce."),
                    Recipe(title = "Chocolate Cake", category = "Desserts", description = "Rich and moist", ingredients = "Flour, Cocoa, Sugar, Eggs", instructions = "Mix and bake."),
                    Recipe(title = "Fruit Salad", category = "Other", description = "Light and fresh", ingredients = "Mixed fruits, Mint", instructions = "Chop and mix.")
                )
                dao.getAllRecipes().let {
                    if (it.isEmpty()) {
                        sampleRecipes.forEach { recipe -> dao.insertRecipe(recipe) }
                        readAllRecipes()
                    }
                }
            }
        }
    }
}