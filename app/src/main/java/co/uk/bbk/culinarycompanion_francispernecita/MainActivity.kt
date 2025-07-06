package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.activity.viewModels
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    // view binding for ui elements access
    private lateinit var binding: ActivityMainBinding
    // view model for the recipes data management
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        // hide the action bar - enabled in the other activities
        supportActionBar?.hide()

        // initialise the recycler view and adapter
        val adapter = RecipeAdapter { selectedRecipe ->
            val intent = Intent(this, RecipeDisplayActivity::class.java)
            intent.putExtra("recipe", selectedRecipe)
            startActivity(intent)
        }
        // setup grid layout for the recipes list
        val layoutManager = GridLayoutManager(this, 2)
        // allow the header to span two columns
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    RecipeAdapter.VIEW_TYPE_HEADER -> 2
                    RecipeAdapter.VIEW_TYPE_ITEM -> 1
                    else -> 1
                }
            }
        }
        // set the layout manager and adapter to the recycler view
        binding.recipeRecyclerView.layoutManager = layoutManager
        binding.recipeRecyclerView.adapter = adapter
        // set the action button for adding recipes
        binding.addRecipeButton.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }
        // assign the dao to the view model
        val dao = RecipesDatabase.getDatabase(applicationContext).recipesDao()
        viewModel.recipesDao = dao

        // fetch the recipes from the database
        viewModel.readAllRecipes()
        // observe the recipes list and update the adapter when it changes
        viewModel.recipes.observe(this) { recipes ->
            // load all the categories from the string array
            val allCategories = resources.getStringArray(R.array.recipe_categories).toList()
            // remove duplicates from the list
            val uniqueRecipes = recipes.distinctBy { it.id }
            // group the recipes by category for header display
            val groupedMap = uniqueRecipes.groupBy { it.category }
            // flatten the map into a list of RecipeListItem
            val flattenedRecipeList = mutableListOf<RecipeListItem>()
            allCategories.forEach { category ->
                flattenedRecipeList.add(RecipeListItem.CategoryHeader(category))

                val recipesInCategory = groupedMap[category] ?: emptyList()
                // Log.d("MainActivity", "Category: $category → ${recipesInCategory.size} items")
                recipesInCategory.forEach { recipe ->
                    flattenedRecipeList.add(RecipeListItem.RecipeItem(recipe))
                }
            }
            // Clear the adapter before submitting the new list
            adapter.submitList(null)
            // Submit the new list to the adapter
            adapter.submitList(flattenedRecipeList)
            // Log.d("MainActivity", "Recipes received: $recipes")
        }
    }
    // reload the recipes list when the activity is resumed
    override fun onResume() {
        super.onResume()
        viewModel.readAllRecipes()
    }
}