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