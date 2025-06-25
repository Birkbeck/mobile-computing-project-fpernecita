package co.uk.bbk.culinarycompanion_francispernecita

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel: ViewModel() {
    private val _recipes = MutableLiveData<ListOf<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    var recipesDao = RecipesDao? = null

    fun readAllRecipes() {
        viewModelScope.launch {
            recipesDao?.let {
                val recipes = it.getAllRecipes()

                Log.i("Recipe", recipes.toString())
                _recipes.value = recipes
            }
        }
    }
}