package co.uk.bbk.culinarycompanion_francispernecita

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ItemRecipeBinding
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ItemCategoryHeaderBinding

class RecipeAdapter(
    private val onClick: (Recipe) -> Unit
) : androidx.recyclerview.widget.ListAdapter<RecipeListItem, RecyclerView.ViewHolder>(RecipeItemDiffCallback())

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecipeListItem.CategoryHeader -> VIEW_TYPE_HEADER
            is RecipeListItem.RecipeItem -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemCategoryHeaderBinding.inflate(inflater, parent, false)
                CategoryHeaderViewHolder(binding)
            }

            else -> {
                val binding = ItemRecipeBinding.inflate(inflater, parent, false)
                RecipeViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is RecipeListItem.CategoryHeader -> (holder as CategoryHeaderViewHolder).bind(item.category)
            is RecipeListItem.RecipeItem -> (holder as RecipeViewHolder).bind(item.recipe)
        }
    }

    class CategoryHeaderViewHolder(private val binding: ItemCategoryHeaderBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            binding.category = category
        }
    }
    class RecipeViewHolder(
        private val binding: ItemRecipeBinding,
        private val onClick: (Recipe) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.recipeTitleTextView.text = recipe.title
            binding.root.setOnClickListener {
                onClick(recipe)
            }
        }
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