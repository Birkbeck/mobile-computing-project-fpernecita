package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityMainBinding
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityAddEditBinding
import co.uk.bbk.culinarycompanion_francispernecita.databinding.DialogConfirmDeleteBinding
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityRecipeDisplayBinding


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
        val dialogBinding = ActivityAddEditBinding.inflate(layoutInflater)
        recipe?.let {
            dialogBinding.recipeTitleEditText.setText(it.title)
            dialogBinding.categorySpinner.setText(it.category)
            dialogBinding.descriptionEditText.setText(it.description)
            dialogBinding.ingredientsEditText.setText(it.ingredients)
            dialogBinding.instructionsEditText.setText(it.instructions)
        }
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()


        dialogBinding.saveButton.setOnClickListener {
            val title = dialogBinding.recipeTitleEditText.text.toString()
            val category = dialogBinding.categorySpinner.text.toString()
            val description = dialogBinding.descriptionEditText.text.toString()
            val ingredients = dialogBinding.ingredientsEditText.text.toString()
            val instructions = dialogBinding.instructionsEditText.text.toString()
            if (title.isNotEmpty() &&
                category.isNotEmpty() &&
                description.isNotEmpty() &&
                ingredients.isNotEmpty() &&
                instructions.isNotEmpty()
            ) {
                if (recipe == null) {
                    viewModel.addRecipe(title, category, description, ingredients, instructions)
                } else {
                    viewModel.editRecipe(recipe.id, title, category, description, ingredients, instructions)
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showDeleteDialog(recipe: Recipe) {
        val dialogBinding = DialogConfirmDeleteBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.confirmDeleteButton.setOnClickListener {
            viewModel.deleteRecipe(recipe)
            dialog.dismiss()
            finish()
        }

        dialogBinding.cancelDeleteButton.setOnClickListener {
            dialog.dismiss()
        }
    }
}