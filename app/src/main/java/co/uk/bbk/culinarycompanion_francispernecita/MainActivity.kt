package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.activity.viewModels
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // enableEdgeToEdge()
        setContentView(binding.root)
        // hide the action bar - enabled in the other activities
        supportActionBar?.hide()


        val adapter = RecipeAdapter { selectedRecipe ->
            val intent = Intent(this, RecipeDisplayActivity::class.java)
            intent.putExtra("recipe", selectedRecipe)
            startActivity(intent)
        }


        // binding.recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        // binding.recipeRecyclerView.layoutManager = GridLayoutManager(this, 2)

        val layoutManager = GridLayoutManager(this, 2)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    RecipeAdapter.VIEW_TYPE_HEADER -> 2
                    RecipeAdapter.VIEW_TYPE_ITEM -> 1
                    else -> 1
                }
            }
        }

        binding.recipeRecyclerView.layoutManager = layoutManager
        binding.recipeRecyclerView.adapter = adapter

        binding.addRecipeButton.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }

        val dao = RecipesDatabase.getDatabase(applicationContext).recipesDao()
        viewModel.recipesDao = dao

//        // dummy test data
//        viewModel.addRecipe(
//            title = "Test Pancakes",
//            category = "Breakfast",
//            description = "Delicious fluffy pancakes with syrup",
//            ingredients = "Flour, Eggs, Milk, Butter",
//            instructions = "1. Mix all ingredients\n2. Cook on pan until golden\n3. Serve with syrup"
//        )

        // trigger readAllRecipes()
        viewModel.readAllRecipes()
        viewModel.recipes.observe(this) { recipes ->
            val allCategories = resources.getStringArray(R.array.recipe_categories).toList()
            val groupedMap = recipes.groupBy { it.category }

//             val groupedRecipes = RecipeViewModel.groupRecipesByCategory(recipes)
//            val flattenedRecipeList = groupedRecipes.flatMap { categoryGroup ->
//                listOf(RecipeListItem.CategoryHeader(categoryGroup.category)) +
//                categoryGroup.recipes.map { RecipeListItem.RecipeItem(it) }
//            }
            val flattenedRecipeList = mutableListOf<RecipeListItem>()
            allCategories.forEach { category ->
                flattenedRecipeList.add(RecipeListItem.CategoryHeader(category))

                val recipesInCategory = groupedMap[category] ?: emptyList()
                recipesInCategory.forEach { recipe ->
                    flattenedRecipeList.add(RecipeListItem.RecipeItem(recipe))
                }
            }
            // Clear the adapter before submitting the new list
            adapter.submitList(null)
            // Submit the new list to the adapter
            adapter.submitList(flattenedRecipeList)
            Log.d("MainActivity", "Recipes received: $recipes")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.readAllRecipes()
    }
}