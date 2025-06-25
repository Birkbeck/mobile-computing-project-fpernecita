package co.uk.bbk.culinarycompanion_francispernecita

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import co.uk.bbk.culinarycompanion_francispernecita.R
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityMainBinding
import co.uk.bbk.culinarycompanion_francispernecita.databinding.RecipesDialogBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val adapter = RecipeAdapter()
        binding.recipeRecyclerView.adapter = adapter

        binding.addRecipeButton.setOnClickListener { showAddRecipeDialog(null) }

        val dao = RecipesDatabase.getInstance(applicationContext).recipesDao()
        viewModel.recipesDao = dao
        viewModel.readAllRecipes()
        viewModel.recipes.observe(this) { recipes ->
            adapter.updateRecipes(recipes)
        }
    }

    // Show the add recipe dialog
    private fun showAddRecipeDialog(recipe: Recipe?) {
        val dialogBinding = RecipesDialogBinding.inflate(layoutInflater)
        recipes?.let {
            dialogBinding.titleEditText.setText(it.title)
            dialogBinding.categoryEditText.setText(it.category)
            dialogBinding.descriptionEditText.setText(it.description)
            dialogBinding.ingredientsEditText.setText(it.ingredients)
            dialogBinding.instructionsEditText.setText(it.instructions)
        }
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
    }

    dialogBinding.saveButton.setOnClickListener
    {
        val title = dialogBinding.titleEditText.text.toString()
        val category = dialogBinding.categoryEditText.text.toString()
        val description = dialogBinding.descriptionEditText.text.toString()
        val ingredients = dialogBinding.ingredientsEditText.text.toString()
        val instructions = dialogBinding.instructionsEditText.text.toString()
        if (title.isNotEmpty() &&
            category.isNotEmpty() &&
            description.isNotEmpty() &&
            ingredients.isNotEmpty() &&
            instructions.isNotEmpty()
        ){
            if (recipe == null) {
                viewModel.addRecipe(title, category, description, ingredients, instructions)
            }
        }
    }