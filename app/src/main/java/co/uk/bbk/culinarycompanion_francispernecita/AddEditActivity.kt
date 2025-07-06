package co.uk.bbk.culinarycompanion_francispernecita

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityAddEditBinding
import co.uk.bbk.culinarycompanion_francispernecita.databinding.DialogConfirmDeleteBinding
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddEditActivity : AppCompatActivity() {

    // view binding for accessing views in the layout
    private lateinit var binding: ActivityAddEditBinding
    // view model for managing data
    private val viewModel: RecipeViewModel by viewModels()
    // hold reference to the recipe being edited
    private var editingRecipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup categories for spinner using string array resource
        val categories = resources.getStringArray(R.array.recipe_categories).toList()
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        binding.categorySpinner.adapter = spinnerAdapter

        // assign the dao to the view model
        viewModel.recipesDao = RecipesDatabase.getDatabase(applicationContext).recipesDao()
        // enable back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Check if recipe is an existing one
        editingRecipe = intent.getSerializableExtra("edit_recipe", Recipe::class.java)

        // supportActionBar?.title = "Add/Edit Recipe"
        // change the title of the action bar depending on whether we are editing or adding a recipe
        supportActionBar?.title = if (editingRecipe != null) "Edit Recipe" else "Add Recipe"

        // populate the fields with the recipe details if editing recipe contents
        editingRecipe?.let { recipe ->
            binding.recipe = recipe

            binding.recipeTitleEditText.setText(recipe.title)
            binding.descriptionEditText.setText(recipe.description)
            binding.ingredientsEditText.setText(recipe.ingredients)
            binding.instructionsEditText.setText(recipe.instructions)

            val categoryIndex = categories.indexOf(recipe.category)
            if (categoryIndex >= 0) {
                binding.categorySpinner.setSelection(categoryIndex)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_edit_menu, menu)
        // hide the delete button if ADDING a new recipe
        if (editingRecipe == null) {
            menu.findItem(R.id.action_delete).isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                performSaveAction()
                true
            }

            R.id.action_delete -> {
                showDeleteDialog()
                true
            }

            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteDialog() {
        val dialogBinding = DialogConfirmDeleteBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.confirmDeleteButton.setOnClickListener {
            editingRecipe?.let {
                viewModel.deleteRecipe(it)
                dialog.dismiss()
                finish()
            }
        }
        dialogBinding.cancelDeleteButton.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }

    private fun performSaveAction() {
        val title = binding.recipeTitleEditText.text.toString()
        val category = binding.categorySpinner.selectedItem.toString()
        val description = binding.descriptionEditText.text.toString()
        val ingredients = binding.ingredientsEditText.text.toString()
        val instructions = binding.instructionsEditText.text.toString()

        if (title.isEmpty() || description.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val dao = viewModel.recipesDao
        if (editingRecipe == null) {
            val newRecipe = Recipe(
                title = title,
                category = category,
                description = description,
                ingredients = ingredients,
                instructions = instructions
            )
            lifecycleScope.launch {
                val recipeId = dao?.insertRecipe(newRecipe)
                if (recipeId != null && recipeId != -1L) {
                    val insertedRecipe = dao.getRecipeById(recipeId)
                    if (insertedRecipe != null) {
                        val intent = Intent(this@AddEditActivity, RecipeDisplayActivity::class.java)
                        intent.putExtra("recipe", insertedRecipe)
                        startActivity(intent)
                    }
                }

                finish()
            }
        } else {
            val updatedRecipe = editingRecipe!!.copy(
                title = title,
                category = category,
                description = description,
                ingredients = ingredients,
                instructions = instructions
            )
            viewModel.editRecipe(updatedRecipe)
            finish()
        }
    }
}