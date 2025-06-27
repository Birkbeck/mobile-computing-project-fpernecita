package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityRecipeDisplayBinding
import co.uk.bbk.culinarycompanion_francispernecita.databinding.DialogConfirmDeleteBinding

import android.view.Menu
import android.view.MenuItem


class RecipeDisplayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDisplayBinding
    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // up button to go back to the main activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Recipe Details"

        recipe = intent.getSerializableExtra("recipe", Recipe::class.java) ?: return finish()

        viewModel.recipesDao = RecipesDatabase.getDatabase(applicationContext).recipesDao()

        bindRecipeDetails()

        binding.editButton.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            intent.putExtra("edit_recipe", recipe)
            startActivity(intent)
        }

        binding.optionsButton.setOnClickListener {
            showDeleteDialog()
        }

    }

    private fun bindRecipeDetails() {
        binding.recipeTitleTextView.text = recipe.title
        binding.categoryTextView.text = recipe.category
        binding.descriptionTextView.text = recipe.description
        binding.ingredientsTextView.text = recipe.ingredients
        binding.instructionsTextView.text = recipe.instructions
    }

    private fun showDeleteDialog() {
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
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.recipe_display_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra("edit_recipe", recipe)
                startActivity(intent)
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}