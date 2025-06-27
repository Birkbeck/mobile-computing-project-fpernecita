package co.uk.bbk.culinarycompanion_francispernecita

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ItemRecipeBinding
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ItemCategoryHeaderBinding

class RecipeAdapter(
    private val onClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(RecipeItemDiffCallback) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_RECIPE = 1
    }
}

//class RecipeAdapter(
//    private var recipes: List<Recipe> = listOf(),
//    private val onClick: (Recipe) -> Unit
//) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
//    // ViewHolder class for each recipe item
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
//        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return RecipeViewHolder(binding)
//    }
//    // Bind data to the view holder
//    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
//        holder.bind(recipes[position])
//    }
//    // get the number of recipes
//    override fun getItemCount(): Int {
//        return recipes.size
//    }
//    // update the list of recipes
//    fun updateRecipes(recipes: List<Recipe>) {
//        this.recipes = recipes
//        notifyDataSetChanged()
//    }
//
//    inner class RecipeViewHolder(private val binding: RecipeItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(recipe: Recipe) {
//            binding.recipe = recipe
//            binding.root.setOnClickListener {
//                onClick(recipe)
//            }
//            binding.executePendingBindings()
//        }
//    }
//}