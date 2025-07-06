package co.uk.bbk.culinarycompanion_francispernecita

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ItemRecipeBinding
import co.uk.bbk.culinarycompanion_francispernecita.databinding.ItemCategoryHeaderBinding

class RecipeAdapter(
    // handle click events on recipe items
    private val onClick: (Recipe) -> Unit
) : androidx.recyclerview.widget.ListAdapter<RecipeListItem, RecyclerView.ViewHolder>(RecipeItemDiffCallback()) {
    // constants for view types on the list
    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
    }
    // determine the view type based on the item type
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecipeListItem.CategoryHeader -> VIEW_TYPE_HEADER
            is RecipeListItem.RecipeItem -> VIEW_TYPE_ITEM
        }
    }
    // create the view holder based on the view type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemCategoryHeaderBinding.inflate(inflater, parent, false)
                CategoryHeaderViewHolder(binding)
            }

            else -> {
                val binding = ItemRecipeBinding.inflate(inflater, parent, false)
                RecipeViewHolder(binding, onClick)
            }
        }
    }
    // bind the data to the corresponding view holder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is RecipeListItem.CategoryHeader -> (holder as CategoryHeaderViewHolder).bind(item.category)
            is RecipeListItem.RecipeItem -> (holder as RecipeViewHolder).bind(item.recipe)
        }
    }
    // view holder for category headers
    class CategoryHeaderViewHolder(private val binding: ItemCategoryHeaderBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            binding.category = category

            // Set the image resource based on the category
            val imageRes = when (category.lowercase()) {
                "breakfast" -> R.drawable.category_breakfast
                "brunch" -> R.drawable.category_brunch
                "lunch" -> R.drawable.category_lunch
                "dinner" -> R.drawable.category_dinner
                "desserts" -> R.drawable.category_desserts
                "other" -> R.drawable.category_other
                else -> R.drawable.category_image_placeholder
            }

            binding.categoryImageView.setImageResource(imageRes)
            // ensure bindings are executed immediately
            binding.executePendingBindings()
        }
    }
    // view holder for individual recipe items
    class RecipeViewHolder(
        private val binding: ItemRecipeBinding,
        private val onClick: (Recipe) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.recipeItem = recipe
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onClick(recipe)
            }
        }
    }
}