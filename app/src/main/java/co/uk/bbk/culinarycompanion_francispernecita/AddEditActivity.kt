package co.uk.bbk.culinarycompanion_francispernecita

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private val viewModel: RecipeViewModel by viewModels()

    private var editingRecipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup categories for spinner
        val categories = listOf("Breakfast", "Brunch", "Lunch", "Dinner", "Desserts", "Other")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        binding.categorySpinner.adapter = spinnerAdapter

        // get DAO
        viewModel.RecipesDao = RecipeDatabase.getInstance(applicationContext).recipesDao(

        // Check if recipe is an existing one
        editingRecipe = intent.getSerializableExtra("edit_recipe") as? Recipe

        editingRecipe?.let { recipe ->
            binding.recipeTitleEditText.setText(recipe.title)
            binding.descriptionEditText.setText(recipe.description)
            binding.ingredientsEditText1.setText(recipe.ingredients)
            binding.instructionsEditText1.setText(recipe.instructions)

            val categoryIndex = categories.indexOf(recipe.category)
            if (categoryIndex >= 0) {
                binding.categorySpinner.setSelection(categoryIndex)
            }
        }

}