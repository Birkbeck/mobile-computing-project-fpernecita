package co.uk.bbk.culinarycompanion_francispernecita

import androidx.recyclerview.widget.DiffUtil
// DiffUtil callback for comparing recipe list items
// based on lecture examples - Mobile Computing
class RecipeItemDiffCallback : DiffUtil.ItemCallback<RecipeListItem>() {
    // Check if two items represent the same object
    override fun areItemsTheSame(oldItem: RecipeListItem, newItem: RecipeListItem): Boolean {
        return oldItem == newItem
    }
    // Check if the contents of two items are the same or have changed
    override fun areContentsTheSame(oldItem: RecipeListItem, newItem: RecipeListItem): Boolean {
        return oldItem == newItem
    }
}