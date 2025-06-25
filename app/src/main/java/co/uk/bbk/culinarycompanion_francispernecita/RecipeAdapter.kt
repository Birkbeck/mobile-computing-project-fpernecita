package co.uk.bbk.culinarycompanion_francispernecita

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycompanion_francispernecita.databinding.RecipeItemBinding

class RecipeAdapter(
    private val recipes: List<Recipe> = listOf()) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    // ViewHolder class for each recipe item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }
    // Bind data to the view holder
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(Recipe[position])
    }

    override fun getItemCount(): Int {
        return Recipe.size
    }

    fun updateRecipes(recipes: List<Recipes>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }
}