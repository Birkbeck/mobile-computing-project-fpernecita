package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.activity.viewModels
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val adapter = RecipeAdapter { selectedRecipe ->
            val intent = Intent(this, RecipeDisplayActivity::class.java)
            intent.putExtra("recipe", selectedRecipe)
            startActivity(intent)
        }

        binding.recipeRecyclerView.adapter = adapter

        binding.addRecipeButton.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }

        val dao = RecipesDatabase.getDatabase(applicationContext).recipesDao()
        viewModel.recipesDao = dao
        viewModel.readAllRecipes()
        viewModel.recipes.observe(this) { recipes ->
            adapter.updateRecipes(recipes)
        }
    }

}