package co.uk.bbk.culinarycompanion_francispernecita

import androidx.recyclerview.widget.DiffUtil

class RecipeItemDiffCallback : DiffUtil.ItemCallback<RecipeListItem>() {
    fun areItemsTheSame(oldItem: RecipeListItem, newItem: RecipeListItem): Boolean {
        return oldItem == newItem
    }
    fun areContentsTheSame(oldItem: RecipeListItem, newItem: RecipeListItem): Boolean {
        return oldItem == newItem
    }

}