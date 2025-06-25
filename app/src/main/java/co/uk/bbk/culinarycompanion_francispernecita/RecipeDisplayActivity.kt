package co.uk.bbk.culinarycompanion_francispernecita

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ActivityRecipeDisplayBinding
import co.uk.bbk.culinarycompanion_francispernecita.databinding.DialogConfirmDeleteBinding

class RecipeDisplayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDisplayBinding
    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var recipe: Recipe
}